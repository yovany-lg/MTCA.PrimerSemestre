(map str [1 2 3])

;Función Anónima
;1
(fn [x] (+ 1 x))
;2
#(+ 1 %1)

;Filter
(filter #(> %1 10)
    [5 11 9 10])

;Reduce
(reduce * [1 2 3 4])

;Función Mayor
(defn greater
    ([] nil)
    ([n] n)
    ([n1 n2]
        (if (nil? n1)
            (if (nil? n2)
                nil
                n2)
            (if (nil? n2)
                n1
                (if (> n1 n2)
                    n1
                    n2)))))

(reduce greater [5 8 10])

(defn invertList
    ([li]
        (invertList (rest li) (list (first li))))
    ([li lifinal]
        (if (empty? li)
            lifinal
            (recur (rest li) (cons (first li) lifinal)))))
(defn myMap
    ([f li]
        (myMap f (rest li) (list (f (first li)))))
    ([f li f_li]
        (if (empty? li)
            (invertList f_li)
            (recur f (rest li) (cons (f (first li)) f_li)))))

(defn myFilter
    ([f li]
        (myFilter f (rest li) (if (f (first li))
                                (list (first li))
                                (list))))
    ([f li final_li]
        (if (empty? li)
            (invertList final_li)
            (recur f (rest li) (if (f (first li))
                                (cons (first li) final_li)
                                final_li)))))
;Promedio
(defn average 
    ([li]
        (if (empty? li)
            0
            (average (rest li) (first li) (count li))))
    ([li sum items]
        (if (empty? li)
            (/ sum items)
            (recur (rest li) (+ sum (first li)) items))))

; '({:nombre "yovany" :edad 28} {:nombre "mauricio" :edad 26})
;({:nombre "yovany" :edad 30} :edad)
;(map (fn [x] (x :edad)) '({:nombre "yovany" :edad 28} {:nombre "mauricio" :edad 26}))
;(28 26)

;Frecuencia
(defn freq
    ([li]
        (if (empty? li)
            '()
            (freq li '())))
    ([li item liOut]  ;Devolver una lista sin las apariciones del elemento buscado
        (if (empty? li)
            liOut
            (recur (rest li) 
                item 
                (if (= item (first li))
                    ;Omitir de la lista de salida al elemento que es igual
                    liOut   
                    ;Solo retornar al resto de los elementos
                    (cons (first li) liOut)))))
    ([li liOut]
        (if (empty? li)
            liOut
            ;Se procesa el primer elemento de la lista
            ;es la única vez que se hará para dicho elemento
            ;Se retorna una lista libre de apariciones de dicho elemento
            (recur (freq li (first li) '()) 
                ;Se genera la lista de apariciones del i-ésimo elemento
                (cons (list (first li) 
                        ;Función que determina el número de apariciones del i-ésimo elemento en la lista original
                        (count (myFilter (fn [x] (= (first li) x)) li)))    
                    liOut)))))

(defn listConcat    ;Concatena la primera lista con la segunda
    ([li1 li2]
        (if (empty? li1)
            (if (empty? li2)
                '()
                li2)
            (if (empty? li2)
                li1
                (listConcat (invertList li1) li2 li2))))
    ([li1 li2 liOut]
        (if (empty? li1)
            liOut
            (recur (rest li1) li2 (conj liOut (first li1))))))



;desviacion estandard
;mediana
;moda