(ns advent.puzzle-4
  (:require [clojure.string :refer [trim]])
  (:require [digest :refer [md5]]))

(defn check-md5 [result prefix]
  (= prefix (subs result 0 (count prefix))))

(defn find-prefix [prefix]
  (let [secret (trim (slurp "4.txt"))]
    (loop [n 0]
      (if (check-md5 (md5 (str secret n)) prefix) n
          (recur (inc n))))))

(defn puzzle-4-a []
  (find-prefix "00000"))

(defn puzzle-4-b []
  (find-prefix "000000"))
