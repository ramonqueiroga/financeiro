(ns financeiro.handler-test
  (:require [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
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

(facts "Saldo inicial é 0"
  (against-background (json/generate-string {:saldo 0}) => "{\"saldo\":0}")
  (let [response (app (mock/request :get "/saldo"))]
    (fact "o formato é 'application/json'"
      (get-in (:headers response) ["Content-Type"]) => "application/json; charset=utf-8")
    (fact "o status da resposta é 200"
      (:status response) => 200)
    (fact "o texto do corpo é um JSON cuja chave é saldo e o valor é 0"
      (:body response) => "{\"saldo\":0}")))

    