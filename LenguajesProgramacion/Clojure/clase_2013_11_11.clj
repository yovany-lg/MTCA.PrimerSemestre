(require '[clojure.string :as ss])

(defn caracteres 
    ;Funcion para obtener una cadena a partir de un caracter repetiro n veces
    ([car cuantos]
        (caracteres car cuantos (str "")))
    ([car cuantos cad]
        (if (> cuantos 0)
            (recur car (dec cuantos) (str cad car))
            cad)))

;Generar nivel con listas
(defn nivel
    ;Caracter de relleno por default
    ([li]
        (nivel li "*"))
    ;Se toma el primer elemento de la lista
    ;(nivel listaRestante caracterRelleno cadenaInicial numeroElemento)
    ([li car]
        (if (not (empty? li))   ;Validar que la lista no este vacía
            (if (= (count li) 1)    ;Si la lista tiene solo un elemento
                (caracteres car (first li))
                (nivel (rest li)
                       car
                       (caracteres " " (first li))
                       2))))  ;En otro caso agregar primeros espacios vacíos
    ([li car cad nitem]
        (if (empty? li) ;Verificar si se llegó al final de la lista
            (clojure.string/join (list cad "\n"))   ;Fin de cadena
            (if (even? nitem)   ;Procesar elementos en posiciones pares e impares
                (recur (rest li)
                       car
                       (clojure.string/join (list cad (caracteres car (first li)))) 
                       (inc nitem))
                (recur (rest li)
                       car
                       (clojure.string/join (list cad (caracteres " " (first li)))) 
                       (inc nitem))))))

;Función que genera la figura a partir de una lista de niveles, 
;los cuales estan dados como lista de espacios-caracteres
(defn figura
    ([li]
        (figura li "*"))
    ([li car]
        (figura (rest li) car (nivel (first li) car)))
    ([li car cad]
        (if (empty? li)
            cad
            (recur (rest li) car (ss/join (list cad (nivel (first li) car)))))))

;Función que genera un triangulo en una lista de niveles, los cuales tienen forma de lista
(defn full_triangle
    ([niveles]
        (full_triangle niveles "*"))
    ([niveles car]
        ))