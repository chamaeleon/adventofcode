(ns advent.puzzle-12
  (:require [clojure.data.json :as json]))

(defn evaluate-node-a [node]
  (cond (number? node) node
        (vector? node) (reduce (fn [sum item] (+ sum (evaluate-node-a item))) 0 node)
        (map? node) (reduce (fn [sum item] (+ sum (evaluate-node-a (second item)))) 0 node)
        :else 0))

(defn evaluate-node-b [node]
  (cond (number? node) node
        (vector? node) (reduce (fn [sum item] (+ sum (evaluate-node-b item))) 0 node)
        (map? node) (if (some #(= (second %) "red") node) 0
                        (reduce (fn [sum item] (+ sum (evaluate-node-b (second item)))) 0 node))
        :else 0))

(defn puzzle-12-a []
  (let [data (json/read-str (slurp "12.txt"))]
    (evaluate-node-a data)))

(defn puzzle-12-b []
  (let [data (json/read-str (slurp "12.txt"))]
    (evaluate-node-b data)))
