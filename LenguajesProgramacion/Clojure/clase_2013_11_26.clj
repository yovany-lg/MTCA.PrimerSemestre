;Del log de acceso proporcionado, extraer la sig. información, a través de una API tipo DSL (Lenguaje de Dominio Específico, Semántica):
;*- IPs y sus cantidades de acceso global y promedio mensual.
;*- IPs mas visitantes y las URLs que han visto.
;Devolver Datos!!, en cualquier formato.
;*- URLs más visitadas, cantidades y las IPs que la visitaron con fecha.
;*- Horas de mas tráfico.
;*- Promedio de visitas diarios, mensuales y anuales.
;*Crear funciones para categorizar URLs.

;Se pueden usar todas las fn de Clojure y los Records, o usar hashMaps

(use 'clojure.java.io)
(require '[clojure.string :as st])

;--------------------Análisis del LOG
(defn getIP
    "Obtiene la IP de una línea de entrada log."
    [line]
    (first (re-find #"([0-9]{1,3}\.){3}([0-9]{1,3}){1}" line)))

(defn getDateTime
    "Obtiene la Hora y Fecha de acceso en una línea de entrada log."
    [line]
    (first (re-find #"\[(0[1-9]|[1-2][0-9]|3[01])[\-\/](Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d):(\d\d):(\d\d):(\d\d)\s([\-\+][\d]{4})\]" line)))

(defn getTime
    "Obtiene la Hora y Fecha de acceso en una línea de entrada log."
    [line]
    (get (re-find #"(20\d\d):((\d\d):(\d\d)):(\d\d)" line) 2))

(defn getURL
    "Obtiene la URL accesada en una línea de entrada log."
    [line]
    (get (re-find #"\p{Punct}([A-Z]+)\s([\/]{1}[\w\p{Punct}]*)\s([\w\p{Punct}]+)\p{Punct}" line) 2))

(defn logLineMap
    "Obtiene un hash-map con claves para IP, dateTime y url de una línea de entrada log."
    [line]
    (hash-map :ip  (getIP line)
            :dateTime (getDateTime line)
            :time (getTime line)
            :url (getURL line)))

(defn logRead
    "Devuelve un vector de hash-maps con las claves para IP, dateTime y url."
    []
    (with-open [rdr (reader "C:/Users/Yovany/Documents/GitHub/MTCA.PrimerSemestre/LenguajesProgramacion/Clojure/buhoz.net.log")]
        (reduce 
            (fn [x y]
                (conj x (logLineMap y)))
            []
            (line-seq rdr))))

(defn groupByIP
    "Agrupa por IPs los accesos de un vector de hash-maps con las claves para IP, dateTime y url.
    Devuelve un vector."
    [logHashVect]
    (seq (group-by :ip logHashVect)))

(defn getDay
    "Obtiene el día, més y año de un hash-map en particular, usando la clave dateTime."
    [hashEntry]
    (first (re-find #"(0[1-9]|[1-2][0-9]|3[01])[\-\/](Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" (hashEntry :dateTime))))

(defn groupByDay
    "Agrupa los accesos de un vector de hash-maps (con las claves para IP, dateTime y url) por día, més y su respectivo año."
    [logHashVect]
    (seq (group-by getDay logHashVect)))

(defn getMonth
    "Obtiene el més y año de un hash-map en particular, usando la clave dateTime."
    [hashEntry]
    (first (re-find #"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" (hashEntry :dateTime))))

(defn groupByMonth
    "Agrupa por més (y su respectivo año) los accesos de un vector de hash-maps con las claves para IP, dateTime y url."
    [logHashVect]
    (seq (group-by getMonth logHashVect)))

(defn getYear
    "Obtiene el año de un hash-map en particular, usando la clave dateTime."
    [hashEntry]
    (peek (re-find #"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" (hashEntry :dateTime))))

(defn groupByYear
    "Agrupa por año los accesos de un vector de hash-maps con las claves para IP, dateTime y url."
    [logHashVect]
    (seq (group-by getYear logHashVect)))

(defn groupByURL
    "Agrupa por url los accesos de un vector de hash-maps con las claves para IP, dateTime y url."
    [logHashVect]
    (seq (group-by :url logHashVect)))

(defn getHour
    "Obtiene la Hora de acceso en una línea de entrada log."
    [hashEntry]
    (get (re-find #"(\d\d):" (hashEntry :time)) 1))

(defn groupByHour
    "Agrupa por url los accesos de un vector de hash-maps con las claves para IP, dateTime y url."
    [logHashVect]
    (seq (group-by getHour logHashVect)))

(defn globalAccess
    "Devuelve la cantidad de acceso global. 
    - Si se usa para un vector de acceso de una IP, el cual se obtiene con la función groupByIP y la clave de la IP, 
    devolverá la cantidad de acceso global de la IP en cuestión."
    [logHashVect]
    (count logHashVect))

(defn dailyAccessAverage
    "Procesa un vector de hash-maps con claves de ip, url y dateTime.
    Independientemente si el vector de hash-maps de entrada esta filtrado por ips,
    devuelve el promedio de accesos por día."
    [logHashVect]  ;Proviene de logRead
    (/ (count logHashVect) (count (groupByDay logHashVect))))

(defn monthlyAccessAverage
    "Procesa un vector de hash-maps con claves de ip, url y dateTime.
    Independientemente si el vector de hash-maps de entrada esta filtrado por ips,
    devuelve el promedio de accesos por mes."
    [logHashVect]  ;Proviene de logRead
    (/ (count logHashVect) (count (groupByMonth logHashVect))))

(defn yearlyAccessAverage
    "Procesa un vector de hash-maps con claves de ip, url y dateTime.
    Independientemente si el vector de hash-maps de entrada esta filtrado por ips,
    devuelve el promedio de accesos por año."
    [logHashVect]  ;Proviene de logRead
    (/ (count logHashVect) (count (groupByYear logHashVect))))

(defn accessByMonth
    "Procesa un vector de hash-maps con claves de ip, url y dateTime.
    Independientemente si el vector de hash-maps de entrada esta filtrado por ips,
    devuelve un vector de hash-maps con claves para el mes y la cantidad de acceso correspondiente."
    ([logHashVect]
        (accessByMonth (groupByMonth logHashVect) []))
    ([monthVect liOut]
        (if (empty? monthVect)
            liOut
                (recur (rest monthVect) (let [monthEntry (first monthVect)]
                (conj liOut (hash-map :month (get monthEntry 0) :monthAccess (globalAccess (get monthEntry 1)))))))))

(defn accessAverageInfo
    [logHashVect]
    (hash-map :dailyAccessAverage (dailyAccessAverage logHashVect)
        :monthlyAccessAverage (monthlyAccessAverage logHashVect)
        :yearlyAccessAverage (yearlyAccessAverage logHashVect)))

(defn visitedURLs
    [logHashVect]  ;Proviene de logRead
    (reduce (fn [x y] (conj x (y :url))) [] logHashVect))

(defn accessInfoByIP
    ([logHashVect]  ;Proviene de logRead
        (accessInfoByIP (groupByIP logHashVect) []))
    ([logHashByIP liOut]
        (if (empty? logHashByIP)
            (sort-by :globalAccess liOut)
            (recur (rest logHashByIP) (let [ipLogVect (first logHashByIP)]
                (conj liOut (hash-map :visitedURLs (visitedURLs (get ipLogVect 1))
                                :accessAverageInfo (accessAverageInfo (get ipLogVect 1))
                                :accessByMonth (accessByMonth (get ipLogVect 1))
                                :globalAccess (globalAccess (get ipLogVect 1))
                                :ip (get ipLogVect 0) )))))))

(defn accessDates
    [logHashVect]
    (map (fn [x] 
            (hash-map :date (get x 0) 
                :globalAccess (globalAccess (get x 1))))
        (groupByDay logHashVect)))

(defn accessDatesByIP
    [logHashVect]
    (map (fn [x] 
            (hash-map :ip (get x 0) 
                :accessDates (accessDates (get x 1)))) 
        (groupByIP logHashVect)))

(defn accessInfoByURL
    ([logHashVect]  ;Proviene de logRead
        (accessInfoByURL (groupByURL logHashVect) []))
    ([logHashByURL liOut]
        (if (empty? logHashByURL)
            (sort-by :globalAccess liOut)
            (recur (rest logHashByURL) (let [urlLogVect (first logHashByURL)]
                (conj liOut (hash-map :globalAccess (globalAccess (get urlLogVect 1))
                                    :accessDatesByIP (accessDatesByIP (get urlLogVect 1))
                                    :url (get urlLogVect 0))))))))

(defn accessInfoByHour
    ([logHashVect]  ;Proviene de logRead
        (accessInfoByHour (groupByHour logHashVect) []))
    ([logHashByHour liOut]
        (if (empty? logHashByHour)
            (sort-by :globalAccess liOut)
            (recur (rest logHashByHour) (let [hourLogVect (first logHashByHour)]
                (conj liOut (hash-map :globalAccess (globalAccess (get hourLogVect 1))
                                    :hour (get hourLogVect 0))))))))





;--------------------------------------------------Código auxiliar y de pruebas

(get (groupByIP (logRead)) "5.10.83.15")
(def dateTime
    ((first (get (groupByIP (logRead)) "5.10.83.15")) :dateTime))
(ipGlobalAccess (get (groupByIP (logRead)) "5.10.83.15"))

(group-by (fn [x] 
        (first (re-find #"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" (x :dateTime)))) 
    ((groupByIP (logRead)) "5.10.83.15"))

(first (re-find #"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" dateTime))
(peek (re-find #"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d)" dateTime))

(groupByMonth (get (groupByIP (logRead)) "5.10.83.15"))
(groupByYear (get (groupByIP (logRead)) "5.10.83.15"))
(groupByIP (logRead))
(let [item (first (groupByIP (logRead)))] item);(conj [] (hash-map :ip (first item) 
        :globalAccess (globalAccess (rest item)))))

["5.10.83.15" [{:url "GET /fotos/index.php/Cosas-del-2008/Julio/Hannover/jardines/P1010835 HTTP/1.1", :dateTime "[26/Oct/2012:02:40:01 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/var/albums/2012/julio-visita-veracruz/P1080777.JPG?m=1344823049 HTTP/1.1", :dateTime "[26/Nov/2013:02:47:38 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/form/add/comments/4897 HTTP/1.1", :dateTime "[26/Nov/2013:02:49:24 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/2012/marzo-monte-alban/P1080127 HTTP/1.1", :dateTime "[26/Nov/2013:02:50:13 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/form/add/comments/4461 HTTP/1.1", :dateTime "[26/Nov/2013:03:07:09 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/2007_001/04/granada/P1000158 HTTP/1.1", :dateTime "[26/Nov/2013:03:39:30 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/Cosas-del-2008/Octubre/barcelona/sagrada_familia/p1020202 HTTP/1.1", :dateTime "[26/Nov/2013:04:08:04 -0600]", :ip "5.10.83.15"}]]
[{:url "GET /fotos/index.php/Cosas-del-2008/Julio/Hannover/jardines/P1010835 HTTP/1.1", :dateTime "[26/Oct/2012:02:40:01 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/var/albums/2012/julio-visita-veracruz/P1080777.JPG?m=1344823049 HTTP/1.1", :dateTime "[26/Nov/2013:02:47:38 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/form/add/comments/4897 HTTP/1.1", :dateTime "[26/Nov/2013:02:49:24 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/2012/marzo-monte-alban/P1080127 HTTP/1.1", :dateTime "[26/Nov/2013:02:50:13 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/form/add/comments/4461 HTTP/1.1", :dateTime "[26/Nov/2013:03:07:09 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/2007_001/04/granada/P1000158 HTTP/1.1", :dateTime "[26/Nov/2013:03:39:30 -0600]", :ip "5.10.83.15"} {:url "GET /fotos/index.php/Cosas-del-2008/Octubre/barcelona/sagrada_familia/p1020202 HTTP/1.1", :dateTime "[26/Nov/2013:04:08:04 -0600]", :ip "5.10.83.15"}]

;*********************************Análisis estadístico
;Se retorna una lista libre de apariciones del elemento
(defn pow [x y] 
    (Math/pow x y))

(defn sqrt [x] 
    (Math/sqrt x))

(defn itemDistinct
    [item li]
    (if (empty? li)
        '()
        (filter (fn [x] (not (= item x))) li)))

;Función que determina el número de apariciones del elemento en una lista
(defn itemCount
    [item li]
    (if (empty? li)
        0
        (count (filter (fn [x] (= item x)) li))))

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

;Frecuencia
(defn freq
    ([li]
        (if (empty? li)
            '()
            (freq li '())))
    ([li liOut]
        (if (empty? li)
            liOut
            ;Se procesa el primer elemento de la lista
            ;es la única vez que se hará para dicho elemento
            ;Se retorna una lista libre de apariciones de dicho elemento
            (recur (itemDistinct (first li) li);(freq li (first li) '()) 
                (cons [(first li) 
                        ;Función que determina el número de apariciones del i-ésimo elemento en la lista actual
                        (itemCount (first li) li)] 
                    liOut)))))

;desviacion estandard
(defn stDeviation
    ([li] 
        (stDeviation li (average li) '()))
    ([li avg liOut]
        (if (empty? li)
            ;s = sqrt(sumatoria((xi - xavg)^2)/(N-1))
            (sqrt (average (rest liOut) (first liOut) (- (count liOut) 1)))
            (recur (rest li) avg (cons (pow (- (first li) avg) 2) liOut)))))
;*********************************

















;        (reduce conj [] (line-seq rdr))))

;        (let [lines (line-seq rdr)]
;            (count lines))))

(defn globalAccessByIP
    "Devuelve la Frecuencia de acceso por IPs"
    [logHashVect]
    (freq (map (fn [x] (x :ip)) 
                logHashVect)))


(defn webLogRead_v1 ;Obsolete
    []
    (let [logMap []]
        (with-open [rdr (reader "C:/Users/Yovany/Documents/GitHub/MTCA.PrimerSemestre/LenguajesProgramacion/Clojure/buhoz.net.log")]
            (doseq [line (line-seq rdr)]
                (conj logMap (hash-map :ip (first (first (getIP line)))
                            :dateTime (first (first (getDateTime line)))
                            :URL (first (first (getURL line)))))))
        logMap))

(def line 
    "5.10.83.15 - - [26/Nov/2013:02:40:01 -0600] \"GET /fotos/index.php/Cosas-del-2008/Julio/Hannover/jardines/P1010835 HTTP/1.1\" 200 9965 \"-\" \"Mozilla/5.0 (compatible; AhrefsBot/5.0; +http://ahrefs.com/robot/)\"")

(def playlist
    [{:title "Elephant", :artist "The White Stripes", :year 2003}
        {:title "Helioself", :artist "Papas Fritas", :year 1997}
        {:title "Stories from the City, Stories from the Sea",
        :artist "PJ Harvey", :year 2000}
        {:title "Buildings and Grounds", :artist "Papas Fritas", :year 2000}
        {:title "Zen Rodeo", :artist "Mardi Gras BB", :year 2002}])

(map :title playlist)
(group-by :artist playlist)

(def orders
    [{:product "Clock", :customer "Wile Coyote", :qty 6, :total 300}
        {:product "Dynamite", :customer "Wile Coyote", :qty 20, :total 5000}
        {:product "Shotgun", :customer "Elmer Fudd", :qty 2, :total 800}
        {:product "Shells", :customer "Elmer Fudd", :qty 4, :total 100}
        {:product "Hole", :customer "Wile Coyote", :qty 1, :total 1000}
        {:product "Anvil", :customer "Elmer Fudd", :qty 2, :total 300}
        {:product "Anvil", :customer "Wile Coyote", :qty 6, :total 900}])
(reduce-by :customer #(+ %1 (:total %2)) 0 orders)

(defn webLogRegex
    [line]
    (re-seq (regexBuild (list ipRegex)) line))

(def ipRegex ;Obtener la IP de la entrada del log.
    "([0-9]{1,3}\\.){3}[0-9]{1,3}{1}")

(def lognameRegex ;Obtener la IP de la entrada del log.
    "([0-9]{1,3}.){3}[0-9]{1,3}{1}")

(defn regexBuild    ;Construye el regex para una lista de regex individualizados
    [li]
    (re-pattern (st/join " " li)))

(let [line "5.10.83.15 - - [26/Nov/2013:02:40:01 -0600] \"GET /fotos/index.php/Cosas-del-2008/Julio/Hannover/jardines/P1010835 HTTP/1.1\" 200 9965 \"-\" \"Mozilla/5.0 (compatible; AhrefsBot/5.0; +http://ahrefs.com/robot/)\""]
    (getIP line))

;(with-open [rdr (reader "clj.bat")]    ;Para cuando se ejecuta desde consola: ...(ruta)>clj myscript.clj, Se carga el archivo desde la ruta actual.
(with-open [rdr (reader "C:/Users/Yovany/Documents/GitHub/MTCA.PrimerSemestre/LenguajesProgramacion/Clojure/buhoz.net.log")]
    (doseq [line (line-seq rdr)]
        (println line)))

(with-open [wrtr (writer "test.txt")]
  (.write wrtr "Line to be written"))
