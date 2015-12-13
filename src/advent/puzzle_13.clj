(ns advent.puzzle-13
  (:require [clojure.math [combinatorics :as comb]]))

(def happy-pattern #"^(\p{Alpha}+) .* (gain|lose) (\p{Digit}+) .* to (\p{Alpha}+)[.]$")

(defrecord Happiness [person happiness neighbor])

(defn parse-happiness [s]
  (if-let [parts (rest (re-find happy-pattern s))]
    (->Happiness (nth parts 0)
                 (if (= (nth parts 1) "gain")
                   (Long/parseLong (nth parts 2))
                   (- (Long/parseLong (nth parts 2))))
                 (nth parts 3))))

(defn add-placement [happiness-map placement]
  (assoc-in happiness-map [(:person placement) (:neighbor placement)]
            (:happiness placement)))

(defn evaluate-placement [happiness-map placement]
  (loop [position 0
         happiness 0]
    (if (= position (count placement))
      happiness
      (let [person (nth placement position)
            left (nth placement (mod (dec position) (count placement)))
            right (nth placement (mod (inc position) (count placement)))]
        (recur (inc position)
               (+ happiness
                  (-> happiness-map (get person) (get left))
                  (-> happiness-map (get person) (get right))))))))

(defn puzzle-13-a []
  (let [placements (map parse-happiness (line-seq (clojure.java.io/reader "13.txt")))
        happiness (reduce add-placement {} placements)
        people (keys happiness)]
    (reduce max (map #(evaluate-placement happiness %) (comb/permutations people)))))

(defn puzzle-13-b []
  (let [placements (map parse-happiness (line-seq (clojure.java.io/reader "13.txt")))
        orig-happiness (reduce add-placement {} placements)
        orig-people (keys orig-happiness)
        happiness (reduce (fn [happiness friend]
                            (-> happiness
                                (assoc-in ["Myself" friend] 0)
                                (assoc-in [friend "Myself"] 0)))
                          orig-happiness
                          orig-people)
        people (keys happiness)]
    (reduce max (map #(evaluate-placement happiness %) (comb/permutations people)))))
