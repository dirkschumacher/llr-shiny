(ns shiny.app)

; small library to make things easier to write
(defmacro output [slot fun body]
  `(r/`<-` (r/$ output ~slot) (~fun ~body)))

(defmacro defui [code]
  `(def ui ~code))

(defmacro defserver [& code]
  `(def server
    (fn [input output session]
        ~@code)))

(defmacro run-app []
  `(r/shinyApp :ui ui :server server))

(r/library shiny)

(defui
  (r/fluidPage
    (r/titlePanel "Old Faithful Geyser Data")
    (r/sidebarLayout
      (r/sidebarPanel
        (r/sliderInput
          "bins" "Number of bins:"
          :min 1 :max 50 :value 30))
        (r/mainPanel (r/plotOutput "distPlot")))))

(defserver
  (output distPlot renderPlot
    (let [x (r/[[ r/datasets::faithful 2)
          bins (r/seq (r/min x) (r/max x) :length.out (+ (r/$ input bins) 1))]
        (r/hist x :breaks bins :col "darkgray" :border "white"))))

; Run the application
(run-app)
