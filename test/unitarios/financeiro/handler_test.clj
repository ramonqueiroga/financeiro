(ns financeiro.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [financeiro.handler :refer :all]))

(facts "Da um 'Olá, mundo!' na rota raiz"
  (let [response (app (mock/request :get "/"))]
    (fact "o status da resposta é 200"
        (:status response) => 200)
    (fact "o texto do corpo é 'Olá, mundo!'"
        (:body response) => "Olá, mundo!")))

(facts "Rota invalida não encontrada"
  (let [response (app (mock/request :get "/invalid"))]
    (fact "o recurso não foi encontrado"
        (:body response) => "Recurso não encontrado")
    (fact "o código de erro é 404"
      (:status response) => 404)))