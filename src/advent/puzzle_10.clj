(ns advent.puzzle-10)

(def digit-pattern #"([0-9])\1*")

(defn encode-digits [s]
  (let [groups (re-seq digit-pattern s)]
    (apply str (map #(str (count (first %)) (second %)) groups))))

(defn puzzle-10-a []
  (let [n (slurp "10.txt")]
    (count (first (drop 40 (iterate encode-digits n))))))

(defn puzzle-10-b []
  (let [n (slurp "10.txt")]
    (count (first (drop 50 (iterate encode-digits n))))))
