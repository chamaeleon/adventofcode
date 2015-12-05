(ns advent.puzzle-1)

(defn puzzle-1-a []
  (let [data (slurp "1.txt")]
    (- (count (filter #(= % \() data))
       (count (filter #(= % \)) data)))))

(defn move [floor n moves]
  (if (= floor -1) n
      (move (+ floor ({\( 1, \) -1} (first moves) 0))
            (inc n)
            (rest moves))))

(defn puzzle-1-b []
  (move 0 0 (slurp "1.txt")))
