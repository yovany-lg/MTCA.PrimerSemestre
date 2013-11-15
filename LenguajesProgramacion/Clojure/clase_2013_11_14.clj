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
(defn mayor
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

(reduce mayor [5 8 10])

(defn myMap
    ([f li]
        (mymap f (rest li) (list (f (first li)))))
    ([f li f_li]
        (if (empty? li)
            (invertList f_li)
            (recur f (rest li) (cons (f (first li)) f_li)))))

(defn invertList
    ([li]
        (invertList (rest li) (list (first li))))
    ([li lifinal]
        (if (empty? li)
            lifinal
            (recur (rest li) (cons (first li) lifinal)))))

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