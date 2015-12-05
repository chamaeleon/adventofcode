(ns advent.puzzle-2
  (:require [clojure.java.io :refer [reader]]))

(defn get-dims [present]
  (map #(Integer. %)
       (rest (re-matches #"([0-9]+)x([0-9]+)x([0-9]+)"
                         present))))

(defn paper [present]
  (let [dims (get-dims present)]
    (let [areas [(* (nth dims 0) (nth dims 1))
                 (* (nth dims 0) (nth dims 2))
                 (* (nth dims 1) (nth dims 2))]]
      (+ (* 2 (reduce + areas)) (apply min areas)))))

(defn puzzle-2-a []
  (let [presents (line-seq (reader "2.txt"))]
    (reduce + (map paper presents))))

(defn ribbon [present]
  (let [dims (get-dims present)]
    (+ (* 2 (apply + (take 2 (sort dims))))
       (apply * dims))))

(defn puzzle-2-b []
  (let [presents (line-seq (reader "2.txt"))]
    (reduce + (map ribbon presents))))
