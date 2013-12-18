;--------------------Solo código visto en clases
;Closures
(defn adder
    [added]
    (fn [x]
        (+ added x)))

(def add5 (adder 5))
(add5 20)

;Parciales: Funciones con algunos parámetros ya definidos
(defn add-xyz   ;Debe ser con defn!!! no def
    [x y z] 
    (+ x y z))

(def add-57z
    (partial add-xyz 5 7))
(add-57z 10)

;Libs en Clojure
;import => Para clases de Java
;require => Pero con nombre distinto
;use => Anexar símbolos al espacio de trabajo actual
(java.util.Random.)
(import 'java.util.Random)
(require '[clojure.string :as st])

(def rnd (java.util.Random.))

(map #(.getName %)  ;Obtener todos los nombres
    ;Obtener los métodos de la clase rnd => java.util.Random.
    (.getMethods (class rnd)))
;(.nextInt rnd)
;(.nextDouble rnd)

(.nextInt (new java.util.Random))

;Como cambiar atributos en un objeto
;Metodos estáticos
;Parse int ...
;Es decir, todo el libro para no fallar