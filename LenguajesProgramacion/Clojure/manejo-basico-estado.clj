;;; Manejo basico de estado (asignacion) en Clojure.
;;;


;; Uso de variables "bindings" locales.
;; Un binding es como ligar un valor a un simbolo.
(let [x 1
      y 2]
  (+ x y))  ; => 3

(+ x y) ; CompilerException java.lang.RuntimeException:
        ; Unable to resolve symbol: x in this context, compiling:(NO_SOURCE_PATH:19:1) 

(defn member
  [li x]
  "Retorna verdadero si x es miembro (esta) en la lista li. Si no, retorna falso."
  (let [filtrada (filter (fn [y] (= y x)) li)
        cuantos (count filtrada)]
    (if (> cuantos 0)
      true
      false)))

;  (member [1 2 3 4] 3)  => true
;  (member [1 2 3 4] 5)  => false

(defn member-rec
  [li x]
  "Retorna verdadero si x es miembro (esta) en la lista li. Si no, retorna falso. Recursivo"
  (if (empty? li)
    false
    (if (= (first li) x)
      true
      (recur (rest li) x))))

;; Para hacer el de frecuencias hacemos lo siguiente:
;; Hay que encontrar los elementos distintos de la lista y meterlos en una nueva.
;; Con los elementos distintos, hay que contar cuantas veces aparece cada elemento
;; en la lista original y agregarlo a una nueva lista con la cantidad de veces
;; que aparecio el elemento.
;; (frecuencias [1  2  3 3  4 4 4 ])  ; => '([1 1] [2 1] [3 2] [4 3])

(defn distintos
  ([li]
   "Devuelve los elementos distintos en la lista"
   (distintos li '()))
  ([li dli]
   (if (empty? li)
     (reverse dli)  ;; Retorno la lista nueva reverseada
     (let [elem (first li)
           rli  (rest li)]
       (if (not (member dli elem))
         (recur rli (cons elem dli))
         (recur rli dli))))))      