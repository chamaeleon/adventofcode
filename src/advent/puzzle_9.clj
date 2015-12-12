(ns advent.puzzle-9
  (:require [clojure.java.io :refer [reader]])
  (:require [clojure.math [combinatorics :as comb]]))

(def route-pattern #"(\p{Alpha}+) to (\p{Alpha}+) = (\p{Digit}+)")

(defrecord Route [from to distance])

(defn parse-route [route]
  (if-let [parts (rest (re-find route-pattern route))]
    [(->Route (nth parts 0) (nth parts 1) (Long/parseLong (nth parts 2)))
     (->Route (nth parts 1) (nth parts 0) (Long/parseLong (nth parts 2)))]))

(defn evaluate-path [routes path]
  (reduce (fn [sum segment]
            (+ sum (some (fn [route]
                           (and (= (:from route) (first segment))
                                (= (:to route) (second segment))
                                (:distance route)))
                         routes)))
          0 path))

(defn puzzle-9-a []
  (let [routes (flatten (filter not-empty (map parse-route (line-seq (reader "9.txt")))))
        cities (into #{} (map :from routes))]
    (apply min (map #(evaluate-path routes (partition 2 1 %))
                    (comb/permutations cities)))))

(defn puzzle-9-b []
  (let [routes (flatten (filter not-empty (map parse-route (line-seq (reader "9.txt")))))
        cities (into #{} (map :from routes))]
    (apply max (map #(evaluate-path routes (partition 2 1 %))
                    (comb/permutations cities)))))
