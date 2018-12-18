(ns landmine.core
  (:gen-class))

(defrecord Dude [current path life])

(defn start-at [x] (->Dude [x 0] [] 100))

(def moves {:north [0 1], :east [1 0], :west [-1 0]})

(defn mine-strength []
  (rand-nth (concat (repeat 10 100) (repeat 30 10) (repeat 60 1))))

(defn populate-mines [m n q]
  (->> (repeatedly #(rand-int (* m n)))
       (map #((juxt quot rem) %1 n))
       (take (* m n (/ 1 100) (- 100 q)))
       (reduce #(assoc %1 (vec %2) (mine-strength)) {})))

(defn print-board [dude m n board]
  (->> (for [y (reverse (range n)), x (range m)] [x y])
       (map #(get board %1 0))
       (partition n)
       (map vec)
       (map println)
       doall))

(defn move-dude [dude m n board]
  (let [dir (rand-nth [:north :east :west])
        [dx dy] (dir moves)
        [x0 y0] (:current dude)
        x (mod (+ x0 dx) n)
        y (+ y0 dy)
        life-current (- (:life dude) (get board [x y] 0))]
    (->Dude [x y] (cons [x y] (:path dude)) life-current)))

(defn traverse-field [dude m n board] 
  (loop [dude dude]
    (cond
      (= (get-in dude [:current 1]) m) dude 
      ((comp not pos?) (:life dude)) dude
      :else (recur (move-dude dude m n board))
    )))

(defn parse-args [args]
  (->> args
       (partition 2)
       (map (juxt (comp keyword first) (comp #(Integer/parseInt %) second)))
       (into {})))

(defn -main
  [& args]
  (let [{:keys [m n q k] :or {m 10, n 10, q 85, k 1000}} (parse-args args)]
    (time
      (->> (range k)
            (pmap #(traverse-field (start-at (rem %1 n)) m n (populate-mines m n q)))
            (filter (comp pos? :life))
            count
            (#(str m "x" n " grid, DRE " q "%: " %1 "/" k " completed, or " (* 100.0 (/ %1 k)) "%"))
            println))))