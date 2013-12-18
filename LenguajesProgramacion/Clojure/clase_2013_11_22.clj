;;Investigar como:
;- Crear un script (bat) para ejecutar clojure.
;- Ejecutar un script que reciba parámetros.
;- En clj. leer y escribir archivos.

;Triangulo relleno SOLO DE EJEMPLO, se copió el código tal cual del archivo clase_2013_11_11.clj
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

;Espacios por nivel para el triangulo relleno
(defn full_triangle_spaces
    ([niveles nivel]
        (- niveles nivel)))

;Caracteres por nivel para el triangulo relleno
(defn full_triangle_chars
    ([nivel]
        (- (* nivel 2) 1)))

;Función que genera un triangulo en una lista de niveles, los cuales tienen forma de lista
(defn full_triangle
    ([niveles]
        (full_triangle niveles "*"))
    ([niveles car]
        (full_triangle niveles ;Niveles
            car ;Caracter de relleno
            (list (list (full_triangle_spaces niveles niveles)
                    (full_triangle_chars niveles)))  ;Lista que contiene a la lista del primer nivel
            (dec niveles)))
    ([niveles car li nivelActual]
        (if (> nivelActual 0)
            (recur niveles 
                car 
                (cons (list (full_triangle_spaces niveles nivelActual) 
                        (full_triangle_chars nivelActual))
                    li)
                (dec nivelActual))
            ;li ;Para regresar la lista de niveles (en forma de lista)
            (figura li car))))


;Obtener Argumentos pasados a la JVM
;(print *command-line-args*)

;Función para obtener en n-ésimo argumento, se debe agregar un desplazamiento de 2,
;debido a que los argumentos desde consola son pasados de la siguiente manera: (-- clase_2013_11_22.clj 5)
(defn nthArg [n] (nth *command-line-args* (+ n 2)))

(defn parse-int [s]
    ;Usa Regex para parsear de una cadena a un entero
    (Integer/parseInt (re-find #"\A-?\d+" s)))

(do
    (let [level (nthArg 0)]
        (if (= level nil)   ;Verificar si el Argumento del nivel existe
            (println "No hay Argumentos de entrada...")
            (println (full_triangle (parse-int  level))))))


;(def m {:a 5 :b 6
;        :c [7 8 9]
;        :d {:e 10 :f 11}
;        "foo" 88
;        42 false})

;(let [{a :a b :b} m]
;    (+ a b))

;(let [a m] (a :a))
;5
;user=> (let [a m] (a :foo))
;nil
;user=> (let [a m] (a "foo"))
;88
;user=> 