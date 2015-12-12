(ns advent.puzzle-11
  (:require [clojure.string :refer [trim]]))

(def letters [\a \b \c \d \e \f \g \h \j \k \m \n \p \q \r \s \t \u \v \w \x \y \z])

(def sequence-pattern #"abc|bcd|cde|def|efg|fgh|ghj|hjk|jkm|kmn|mnp|npq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz")
(def pair-pattern #"(\p{Alpha})\1")

(defn check-sequence [s]
  (re-find sequence-pattern s))

(defn check-pairs [s]
  (>= (count (re-seq pair-pattern s)) 2))

(defn check [s]
  (and (check-sequence s)
       (check-pairs s)))

(defn encode-password [s]
  (reduce (fn [sum ch] (+ (* (count letters) sum)
                          (.indexOf letters ch)))
          0N s))

(defn decode-password [n]
  (loop [iterations 0
         s ""
         n n]
    (if (= iterations 8) s
        (recur (inc iterations)
               (str (letters (rem n (count letters))) s)
               (quot n (count letters))))))

(defn generate-valid-password [n]
  (let [password (decode-password n)]
    (if (check password)
      password
      (recur (inc n)))))

(defn puzzle-11-a []
  (let [current (trim (slurp "11.txt"))
        encoded (inc (encode-password current))]
    (generate-valid-password encoded)))

(defn puzzle-11-b []
  (let [current (puzzle-11-a)
        encoded (inc (encode-password current))]
    (generate-valid-password encoded)))
