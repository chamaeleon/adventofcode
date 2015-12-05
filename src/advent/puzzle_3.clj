(ns advent.puzzle-3)

(defn valid [dir]
  (#{\^ \v \< \>} dir))

(defn move [[x y] dir]
  (cond (= dir \^) [x (inc y)]
        (= dir \v) [x (dec y)]
        (= dir \<) [(dec x) y]
        (= dir \>) [(inc x) y]))

(defn travel [houses pos moves]
  (if (empty? moves) houses
      (let [newpos (move pos (first moves))]
        (recur (update-in houses [newpos] (fnil inc 0))
               newpos
               (rest moves)))))

(defn puzzle-3-a []
  (let [directions (filter valid (slurp "3.txt"))]
    (count (travel {[0 0] 1} [0 0] directions))))

(defn puzzle-3-b []
  (let [directions (filter valid (slurp "3.txt"))
        santa-moves (map first (partition 2 directions))
        robo-moves (map second (partition 2 directions))
        houses (travel {[0 0] 1} [0 0] santa-moves)
        houses (travel houses [0 0] robo-moves)]
    (count houses)))
