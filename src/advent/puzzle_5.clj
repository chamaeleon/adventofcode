(ns advent.puzzle-5)

(defn check1 [s]
  (re-find #"[aeiou].*[aeiou].*[aeiou]" s))

(defn check2 [s]
  (re-find #"([a-z])\1" s))

(defn check3 [s]
  (not (re-find #"(ab)|(cd)|(pq)|(xy)" s)))

(defn puzzle-5-a []
  (let [strs (line-seq (clojure.java.io/reader "5.txt"))]
    (count (filter #(and (check1 %) (check2 %) (check3 %)) strs))))

(defn check1-b [s]
  (re-find #"([a-z][a-z]).*\1" s))

(defn check2-b [s]
  (re-find #"([a-z]).\1" s))

(defn puzzle-5-b []
  (let [strs (line-seq (clojure.java.io/reader "5.txt"))]
    (count (filter #(and (check1-b %) (check2-b %)) strs))))
