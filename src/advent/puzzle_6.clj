(ns advent.puzzle-6)

(defn bget [grid i j]
  (aget ^booleans (aget ^objects grid i) j))

(defn bset [grid i j value]
  (aset-boolean (aget ^objects grid i) j value))

(defn lget [grid i j]
  (aget ^longs (aget ^objects grid i) j))

(defn lset [grid i j value]
  (aset-long (aget ^objects grid i) j value))

(defn make-rect [corners]
  (let [corners (map #(Integer/parseInt %) corners)]
    [(min (nth corners 0) (nth corners 2))
     (min (nth corners 1) (nth corners 3))
     (max (nth corners 0) (nth corners 2))
     (max (nth corners 1) (nth corners 3))]))

(def spec-pattern #"(turn on|turn off|toggle) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)")

(defn parse-spec [s]
  (if-let [parts (rest (re-find spec-pattern s))]
    {:mode (cond (= (first parts) "turn on") :on
                 (= (first parts) "turn off") :off
                 (= (first parts) "toggle") :toggle)
     :rect (make-rect (rest parts))}))

(defn apply-spec-a [grid spec]
  (let [[a b c d] (:rect spec)]
    (doseq [i (range a (inc c))
            j (range b (inc d))]
      (cond (= (:mode spec) :on) (bset grid i j true)
            (= (:mode spec) :off) (bset grid i j false)
            (= (:mode spec) :toggle) (bset grid i j (not (bget grid i j)))))))

(defn puzzle-6-a []
  (let [specs (map parse-spec (line-seq (clojure.java.io/reader "6.txt")))
        grid (make-array Boolean/TYPE 1000 1000)]
    (dorun (doseq [spec specs]
             (apply-spec-a grid spec)))
    (count (filter true? (flatten (map vec (vec grid)))))))

(defn apply-spec-b [grid spec]
  (let [[a b c d] (:rect spec)]
    (doseq [i (range a (inc c))
            j (range b (inc d))]
      (cond (= (:mode spec) :on) (lset grid i j (inc (lget grid i j)))
            (= (:mode spec) :off) (lset grid i j (max 0 (dec (lget grid i j))))
            (= (:mode spec) :toggle) (lset grid i j (+ 2 (lget grid i j)))))))

(defn puzzle-6-b []
  (let [specs (map parse-spec (line-seq (clojure.java.io/reader "6.txt")))
        grid (make-array Long/TYPE 1000 1000)]
    (dorun (doseq [spec specs]
             (apply-spec-b grid spec)))
    (reduce + (flatten (map vec (vec grid))))))
