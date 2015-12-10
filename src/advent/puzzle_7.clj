(ns advent.puzzle-7)

(def gate-pattern #"(?:([a-z0-9]+) )?(?:(AND|OR|LSHIFT|RSHIFT|NOT) )?([a-z0-9]+) -> ([a-z]+)")
(defrecord gate [a op b wire value])

(defn parse-gate [definition]
  (rest (re-find gate-pattern definition)))

(defn fix-constant [gate attribute]
  (if (and (attribute gate)
           (re-find #"^[0-9]+$" (get gate attribute)))
    (assoc gate attribute (Long/parseLong (get gate attribute)))
    gate))

(defn set-value [circuit wire value]
  (assoc-in circuit [wire :value] value))

(defn get-value [circuit wire]
  (if (= (class wire) Long) wire
      (:value (circuit wire))))

(defn make-gate [gate-data]
  (-> (apply ->gate (-> gate-data vec (conj nil)))
      (fix-constant :a)
      (fix-constant :b)))

(defn add-gate [circuit gate]
  (assoc circuit (:wire gate) gate))

(defn check-input [circuit gate input]
  (cond (= Long (class (input gate))) true
        (not (nil? (:value (circuit (input gate))))) true
        :else false))

(defn check-one-input [circuit gate]
  (check-input circuit gate :b))

(defn check-two-inputs [circuit gate]
  (and (check-input circuit gate :a)
       (check-input circuit gate :b)))

(defn can-evaluate [circuit wire]
  (let [gate (circuit wire)]
    (and (nil? (:value gate))
         (cond (or (nil? (:op gate))
                   (= "NOT" (:op gate))) (check-one-input circuit gate)
               :else (check-two-inputs circuit gate)))))

(defn update-circuit [circuit wire]
  (let [gate (circuit wire)
        op (:op gate)]
    (set-value circuit wire
               (bit-and 65535
                        (cond (nil? op) (get-value circuit (:b gate))
                              (= op "NOT") (bit-not (get-value circuit (:b gate)))
                              (= op "AND") (bit-and (get-value circuit (:a gate))
                                                    (get-value circuit (:b gate)))
                              (= op "OR") (bit-or (get-value circuit (:a gate))
                                                  (get-value circuit (:b gate)))
                              (= op "LSHIFT") (bit-shift-left (get-value circuit (:a gate))
                                                              (get-value circuit (:b gate)))
                              (= op "RSHIFT") (bit-shift-right (get-value circuit (:a gate))
                                                               (get-value circuit (:b gate))))))))

(defn evaluate-circuit [circuit]
  (let [possible (filter #(if (can-evaluate circuit %) %) (keys circuit))]
    (if (empty? possible)
      circuit
      (evaluate-circuit (update-circuit circuit (first possible))))))

(defn puzzle-7-a []
  (let [gates (map make-gate
                   (filter #(not (empty? %))
                           (map parse-gate (line-seq (clojure.java.io/reader "7.txt")))))
        circuit (reduce add-gate {} gates)
        evaluated (evaluate-circuit circuit)]
    (:value (evaluated "a"))))

(defn puzzle-7-b []
  (let [gates (map make-gate
                   (filter #(not (empty? %))
                           (map parse-gate (line-seq (clojure.java.io/reader "7.txt")))))
        circuit (set-value (reduce add-gate {} gates) "b" (puzzle-7-a))
        evaluated (evaluate-circuit circuit)]
    (:value (evaluated "a"))))
