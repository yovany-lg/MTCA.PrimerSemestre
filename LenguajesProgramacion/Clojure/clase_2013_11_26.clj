;Del log de acceso proporcionado, extraer la sig. información, a través de una API tipo DSL (Lenguaje de Dominio Específico, Semántica):
;- IPs y sus cantidades de acceso global y promedio mensual.
;- IPs mas visitantes y las URLs que han visto.
;Devolver Datos!!, en cualquier formato.
;- URLs más visitadas, cantidades y las IPs que la visitaron con fecha.
;- Horas de mas tráfico.
;- Promedio de visitas diarios, mensuales y anuales.
;*Crear funciones para categorizar URLs.

;Se pueden usar todas las fn de Clojure y los Records, o usar hashMaps

(use 'clojure.java.io)
(require '[clojure.string :as st])

(defn getIP ;Obtener la IP de la entrada del log.
    [line]
    (re-seq #"([0-9]{1,3}\.){3}([0-9]{1,3}){1}" line))

(defn getDateTime ;Obtener la hora y fecha
    [line]
    (re-seq #"\[(0[1-9]|[1-2][0-9]|3[01])[\-\/](Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[\-\/](20\d\d):(\d\d):(\d\d):(\d\d)\s([\-\+][\d]{4})\]" line))

(defn getURL ;Obtener la URL accesada
    [line]
    (re-seq #"\p{Punct}([A-Z]+)\s([\/]{1}[\w\p{Punct}]*)\s([\w\p{Punct}]+)\p{Punct}" line))

(defn webLogRead
    []
    (let [logMap []]
        (with-open [rdr (reader "C:/Users/Yovany/Documents/GitHub/MTCA.PrimerSemestre/LenguajesProgramacion/Clojure/buhoz.net.log")]
            (doseq [line (line-seq rdr)]
                (conj logMap (hash-map :ip (getIP line) :dateTime (getDateTime line) :URL (getURL line)))))
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
