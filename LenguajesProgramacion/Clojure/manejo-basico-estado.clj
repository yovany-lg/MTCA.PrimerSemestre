;;; Manejo basico de estado (asignacion) en Clojure.
;;;


;; Uso de variables "bindings" locales.
;; Un binding es como ligar un valor a un simbolo.
(let [x 1
      y 2]
  (+ x y))  ; => 3

(+ x y) ; CompilerException java.lang.RuntimeException:
        ; Unable to resolve symbol: x in this context, compiling:(NO_SOURCE_PATH:19:1) 

;; Uso de variables "globales". Hay que tener cuidado al usarlas.
;; Se utilizan para definir constantes o valores que no van a cambiar mucho o nada
;; durante la ejecucion del programa. Por ejemplo, conexiones a dbs, parametros
;; globales, etc.

(def definido (list 1 2 3))  ; Le asigno la lista '(1 2 3) al simbolo "definido"
(println definido)  ; -> (1 2 3) nil
(def fhola (fn [x] (str "Hola " x)))  ; Le asigno una fn al simbolo "fhola"
(fhola "jose") ; ->  "Hola jose"

;;;;;;;;;;;;;;;;;;

(defn member
  "Retorna verdadero si x es miembro (esta) en la lista li. Si no, retorna falso."
  [li x]
  (let [filtrada (filter (fn [y] (= y x)) li)
        cuantos (count filtrada)]
    (if (> cuantos 0)
      true
      false)))

;  (member [1 2 3 4] 3)  => true
;  (member [1 2 3 4] 5)  => false

(defn member-rec
  "Retorna verdadero si x es miembro (esta) en la lista li. Si no, retorna falso. Recursivo"
  [li x]
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
  "Devuelve los elementos distintos en la lista"
  ([li]
   (distintos li '()))
  ([li dli]
   (if (empty? li)
     (reverse dli)  ;; Retorno la lista nueva reverseada
     (let [elem (first li)
           rli  (rest li)]
       (if (member dli elem)
         (recur rli dli)
         (recur rli (cons elem dli)))))))

(defn ecount
 "Devuelve la cantidad de veces que se encuentra el elemento x en la lista li."
  [li x]
  (count (filter #(= %1 x) li)))

; (ecount [1 2 2 3 3 3 4] 4) => 1
; (ecount [1 2 2 3 3 3 4] 3) => 3

(defn frecuencias
  "Devuelve una lista de vectores, en los que cada vector es cada elemento de la lista
   junto con la cantidad de veces que aparece en la lista li."
  [li]
  (map #(vector %1 (ecount li %1)) (distintos li)))
 
; (frecuencias [1 2 2 3 3 3 4 4 5 6 6 7 7 8 1 9 2 3 4])
; =>  ([1 2] [2 3] [3 4] [4 3] [5 1] [6 2] [7 2] [8 1] [9 1])