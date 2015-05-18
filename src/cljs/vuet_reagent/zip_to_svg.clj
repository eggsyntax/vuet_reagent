(ns vuet-reagent.zip-to-svg
  (:require [clojure.zip :as z]
            [clojure.walk :refer [postwalk]]
            [clojure.pprint :refer [pprint]]))


; Ellipse radius
(def rx 20)
(def ry 20)

; Y offset between levels of the zipper
(def level-offset 50)

; X offset between sibling ellipses
(def sibling-offset 50)

(def testz '[(0 1 ((3 4) 2)) {:changed? true}])
(pprint testz)

(def testz2 '[((3 4) 2) {:l [0 1], :pnodes ((0 1 ((3 4) 2))), :ppath {:changed? true}, :r nil}])
(pprint testz2)

(defn printwalk [zp]
  (let [print-fn (fn [form] (prn form))
        printed (postwalk print-fn zp)]
    (pprint printed)))

(printwalk [1 2 3])
(printwalk testz2)

(defn printeach [zp]
  (let [print-fn (fn [form] (vector (str form \n)))]
    (map pprint zp)))

(printeach [1 2 3])
(printeach testz2)

(defn svg-ellipse
  "Convert a zipper node to an ellipse"
  [xpos ypos]
  [:ellipse {:cx xpos
             :cy ypos
             :rx rx
             :ry ry
             :style {:fill :yellow :stroke "#AAA"}}])

(defn svg-text
  "Convert a zipper node to text on the screen"
  [text xpos ypos]
  [:text {:x xpos
          :y (+ ypos 5)
          :text-anchor "middle"}
   text])

; (defn get-new-xs
;   ;TODO you are here
;   ; Get a set of offsets, extending equally far to the left and right, based on
;   ; how many members form has.
;   ""
;   [form x]
;   (for [i (repeat (count form) inc)])
;     ; ?????????
;   )
(defn to-svg
  "Convert a zipper to a nested svg hiccup form. Returns a vector of the two forms for display."
  ([form]
   (to-svg form 0 200 50))

  ([form depth x y]
   (if (seq? form)
     (let [new-depth (+ 1 depth)
           new-y (+ level-offset y)
           new-xs (get-new-xs form x)
           ; We want to map over both the sequence and a series of x-offsets:
           to-svg-partial #(to-svg % new-depth % new-y)]
       [(map to-svg-partial form new-xs)])
     ; form is not a seq. So it's a number or
     ; whatever text should be represented on screen
     [(svg-ellipse x y) (svg-text form x y)])))

(defn zip-to-svg
  [zip]
  (let [[form metad] zip]
    [(to-svg form) metad]))

(def testz-data (first testz))
(pprint testz-data)
(pprint (zip-to-svg testz))
