(ns advent.core
  (:require [advent.puzzle-1 :refer [puzzle-1-a puzzle-1-b]])
  (:require [advent.puzzle-2 :refer [puzzle-2-a puzzle-2-b]])
  (:require [advent.puzzle-3 :refer [puzzle-3-a puzzle-3-b]])
  (:require [advent.puzzle-4 :refer [puzzle-4-a puzzle-4-b]])
  (:require [advent.puzzle-5 :refer [puzzle-5-a puzzle-5-b]]))

(defn -main []
  (println "Puzzle 1a: " (puzzle-1-a))
  (println "Puzzle 1b: " (puzzle-1-b))
  (println "Puzzle 2a: " (puzzle-2-a))
  (println "Puzzle 2b: " (puzzle-2-b))
  (println "Puzzle 3a: " (puzzle-3-a))
  (println "Puzzle 3b: " (puzzle-3-b))
  (println "Puzzle 4a: " (puzzle-4-a))
  (println "Puzzle 4b: " (puzzle-4-b))
  (println "Puzzle 5a: " (puzzle-5-a))
  (println "Puzzle 5b: " (puzzle-5-b))
  )
