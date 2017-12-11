(ns tic-tac-toe.core
  (:require [reagent.core :as r]))

(def squares (r/atom (vec (repeat 9 nil))))
(def x-is-next (r/atom true))

(defn get-move [] (if @x-is-next "X" "O"))

(defn get-winner []
  (reduce #(if (not= %1 nil) %1
             (if (= (nth @squares (nth %2 0))
                    (nth @squares (nth %2 1))
                    (nth @squares (nth %2 2)))
               (nth @squares (nth %2 0))))
          nil
          [[0 1 2]
           [3 4 5]
           [6 7 8]
           [0 3 6]
           [1 4 7]
           [2 5 8]
           [0 4 8]
           [2 4 6]]))


(defn square [value func]
  [:button.square {:on-click func} value])

(defn handle-click [pos]
  (if (= (get-winner) nil)
    (do
      (swap! squares assoc pos (get-move))
      (swap! x-is-next not))))

(defn render-square [pos]
  [square (nth @squares pos) #(handle-click pos)])

(defn get-status []
  (let [winner (get-winner)]
    (if (= winner nil)
      (str "Next Player: " (get-move))
      (str "Winner: " winner))))

(defn board []
  (let [status (get-status)]
    [:div
     [:div.status status]
     [:div.board-row
      [render-square 0]
      [render-square 1]
      [render-square 2]]
     [:div.board-row
      [render-square 3]
      [render-square 4]
      [render-square 5]]
     [:div {:class "board-row"}
      [render-square 6]
      [render-square 7]
      [render-square 8]]]))

(defn game []
  [:div.game
   [:div.game-board [board]]
   [:div.game-info]])

(r/render-component [game] (. js/document (getElementById "app")))
