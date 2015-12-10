(ns advent.utils)

(defn bget [grid i j]
  (aget ^booleans (aget ^objects grid i) j))

(defn bset [grid i j value]
  (aset-boolean (aget ^objects grid i) j value))

(defn lget [grid i j]
  (aget ^longs (aget ^objects grid i) j))

(defn lset [grid i j value]
  (aset-long (aget ^objects grid i) j value))
