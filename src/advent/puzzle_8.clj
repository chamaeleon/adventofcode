(ns advent.puzzle-8
  (:require [clojure.java.io :refer [reader]])
  (:require [clojure [string :as str]]))

(def string-pattern #"[a-zA-Z0-9]|\\\"|\\x[0-9a-f]{2}|\\\\")

(defn characters [s]
  (count (re-seq string-pattern s)))

(defn puzzle-8-a []
  (let [strings (map str/trim (line-seq (reader "8.txt")))]
    (reduce (fn [sum s] (+ sum (- (count s) (characters s))))
            0 strings)))

(defn escape-string [s]
  (str \" (-> s
              (str/replace "\\" "\\\\")
              (str/replace "\"" "\\\""))
       \"))

(defn puzzle-8-b []
  (let [strings (map str/trim (line-seq (reader "8.txt")))]
    (reduce (fn [sum s] (+ sum (- (count (escape-string s)) (count s))))
            0 strings)))
