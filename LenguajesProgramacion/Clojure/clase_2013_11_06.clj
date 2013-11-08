(defn functionx 
    [p1 p2] 
    "Documentacion de mi funcion"
    (str p1 p2))

;Funcion anonima, identidad.
(fn [p1] p1)
((fn [p1] p1) '(:a :b :c)) ;Con valor

(count "123")
(count '(1 2 3))
(first '(1 2 3))
(rest '(1 2 3))
(last '(1 2 3))
(empty? "")

(defn contar
    ([li]
        (contar li 0))
    ([li cuantos]
        (if (empty? li)
            cuantos
            (recur (rest li)
                (inc cuantos)))))

;Tarea: implementar el ultimo
(defn ultimo
    "Para el caso general"
    ([li]
        (if (> (count li) 1)
            (recur (rest li))
            (nth li 0))))

;Nueva implementacion
(defn ultimo_2
    ;No se puede implementar al igual que Haskell, 
    ;en donde se puede procesar una lista de un solo elemento
    ;aqui se deben hacer comparaciones... 
    [li]
    (if (empty? (rest li))
        (first li)
        (recur (rest li))))

;Secuencias
(list 1 2 3 4 5)
(cons 8 (list 1 2 3 4 5))

;Realizar la sig. fn.:
;(rango 10) = '(0 1 2 3 4 5 6 7 8 9 10)
(defn rango
    ([n]
        (rango (dec n) (list n)))

    ([n li]
        (if (>= n 0)
            (recur (dec n) (cons n li))
            li)))
