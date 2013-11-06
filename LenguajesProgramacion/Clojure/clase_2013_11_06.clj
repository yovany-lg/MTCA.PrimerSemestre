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