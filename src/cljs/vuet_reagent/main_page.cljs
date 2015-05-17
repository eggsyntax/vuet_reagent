(ns vuet-reagent.main-page
    (:require [reagent.core :as reagent :refer [atom]]
              [vuet-regent.huet :as h]              
;               [reagent.session :as session]
;               [secretary.core :as secretary :include-macros true]
;               [goog.events :as events]
;               [goog.history.EventType :as EventType]
;               [cljsjs.react :as react]
              )
    (:import goog.History))

(defonce default-db-value
  {:name "reagent vuet db"
   :zipper (h/new-zipper)})

; Lovely easy-access global variable. I mean database.
(defonce db (atom default-db-value))

(defn act-on-keypress [event & args]
  ; TODO only do + 32 if it's in the A..Z range
  (let [lower-int (fn [v] (+ v 32))
        keychar (-> event .-which lower-int char)]
    (println "keychar: " keychar)
    (swap! db assoc :zipper 
           (h/act-on (:zipper @db) keychar)) 
    (println @db)))

(defn z-node []
  [:svg {:height 100
           :width 500}
     [:ellipse {:cx 220
                :cy 50
                :rx 20
                :ry 20
                :style {:fill :yellow}
                }]
     ])

(defn spaces
  "Get some spaces for padding a string"
  [n]
  (apply str (take n (repeat \space))))

(defn formatted-zip
  ([z] (formatted-zip z 0))
  ([z depth]
  (let [deeper (+ 1 depth)
        deeper-format (fn [x] (formatted-zip x deeper))]
    (println "Args " z depth)
    (cond
      (string? z) z
      (seq? z) (apply str (map deeper-format z))
      (vector? z) (apply #(str % "\n") (map deeper-format z))
      (map? z) (apply #(str % "\n") z)
      ;(map? z) (into {} (formatted-zip (seq z) deeper))
      :else (str "\n " (spaces depth) z)
      )))
  )

(defn main []
 (fn [] 
   (let [[z metad] (:zipper @db)]
     [:div
      [:input {:on-key-down act-on-keypress}]
      [:p]
      [:pre (formatted-zip z)]
      [:pre (str metad)] ;print metadata
      [z-node]
      ])))
