package io.zardo.hackaixa.dialogflow;

import java.util.HashMap;
import java.util.Map;

public class DialogflowService {

    private static DialogflowService instance;

    private Map<String, Situacao> sessoes = new HashMap<>();

    private Map<String, String> nomes = new HashMap<>();

    private Map<String, String> respostasBounds = new HashMap<>();

    private Map<String, Dificuldade> dificuldades = new HashMap<>();

    private Map<String, Integer> pontuacao = new HashMap<>();

    public static DialogflowService getInstance() {
        if (instance == null) instance = new DialogflowService();
        return instance;
    }

    public void setarSituacaoSessao(String sessao, Situacao situacao) {
        sessoes.put(sessao, situacao);
    }

    public Situacao buscarSituacaoSessao(String sessao) {
        Situacao situacao = sessoes.get(sessao);
        if (situacao == null) return Situacao.PERGUNTANDO_NOME;
        return situacao;
    }

    public void setarNome(String session, String nome) {
        nomes.put(session, nome);
    }

    public String buscarNome(String sessao) {
        String nome = nomes.get(sessao);
        if (nome == null || nome.equals("")) nome = "(qual era seu nome mesmo?)";
        return nome;
    }

    public void setarDificuldade(String session, Dificuldade dificuldade) {
        dificuldades.put(session, dificuldade);
        pontuacao.put(session, dificuldade.pontos);
    }


    public String pedirAjuda(String session) {
        Dificuldade dificuldade = dificuldades.get(session);
        if (dificuldade != null) {
            return dificuldade.tesouro.dica;
        }

        return "";
    }

    public boolean validarResposta(String session, String placeId) {
        Dificuldade dificuldade = dificuldades.get(session);
        if (dificuldade == null) return false;

        try {
            if (dificuldade.tesouro.equals(Tesouro.buscarTesouro(placeId))) return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public boolean validarResposta(String session, Tesouro tesouro) {
        Dificuldade dificuldade = dificuldades.get(session);
        if (dificuldade == null) return false;

        try {
            if (dificuldade.tesouro.equals(tesouro)) return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public String buscarDica(String session) {
        Dificuldade dificuldade = dificuldades.get(session);
        return dificuldade.tesouro.descricaoLocal;
    }

    public String compararBounds(String session, Parameters parameters) {
        Dificuldade dificuldade = dificuldades.get(session);
        if (parameters.getLat1() != null && parameters.getLat2() != null && parameters.getLng1() != null && parameters.getLng2() != null) {
            double lat1 = parameters.getLat2();
            double lat2 = parameters.getLat1();
            double lon1 = parameters.getLng1();
            double lon2 = parameters.getLng2();
            if (lat1 > 0) lat1 = lat1 * (-1);
            if (lat2 > 0) lat2 = lat2 * (-1);
            if (lon1 > 0) lon1 = lon1 * (-1);
            if (lon2 > 0) lon2 = lon2 * (-1);

            // Ponto está dentro dos bounds do mapa
            if (lat1 > dificuldade.tesouro.lat && lat2 < dificuldade.tesouro.lat && lon1 < dificuldade.tesouro.lng && lon2 > dificuldade.tesouro.lng) {
                double distanciaCobertaMapa = calcularDistancia(lat1, lat2, lon1, lon2, 0.0, 0.0);
                if (distanciaCobertaMapa < 500) {
                    return validarRetornoBounds(session, "Clica! Clica! Clica! Estou ficando sem paciência!");
                } else if (distanciaCobertaMapa < 1000) {
                    return validarRetornoBounds(session, "Não precisa cavar agora, viu? É só clicar no ponto!");
                } else if (distanciaCobertaMapa < 2000) {
                    return validarRetornoBounds(session, "Você já sabe, né? Eu acho que está só me testando! Clica logo!");
                } else if (distanciaCobertaMapa < 5000) {
                    return validarRetornoBounds(session, "Eu já vi o ponto e acho que você também! Agora é só dar mais zoom e clicar no ponto!");
                } else if (distanciaCobertaMapa < 25000) {
                    return validarRetornoBounds(session, "Está chegando perto! Aproxime mais um pouquinho!");
                } else {
                    return validarRetornoBounds(session, "Estou vendo o tesouro daqui de cima! Que tal aumentar o zoom?");
                }
            } else if (lat1 < dificuldade.tesouro.lat) { // está ao norte
                if (lon1 < dificuldade.tesouro.lng && lon2 > dificuldade.tesouro.lng)
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao NORTE de onde você está.");
                else if (lon2 < dificuldade.tesouro.lng) // nordeste
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao NORDESTE de onde você está.");
                else if (lon1 > dificuldade.tesouro.lng) // noroeste
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao NOROESTE de onde você está.");
            } else if (lat2 > dificuldade.tesouro.lat) { // está ao sul
                if (lon1 < dificuldade.tesouro.lng && lon2 > dificuldade.tesouro.lng)
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao SUL de onde você está.");
                else if (lon2 < dificuldade.tesouro.lng) // nordeste
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao SUDESTE de onde você está.");
                else if (lon1 > dificuldade.tesouro.lng) // noroeste
                    return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao SUDOESTE de onde você está.");
            } else if (lon2 < dificuldade.tesouro.lng) { // leste
                return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao LESTE de onde você está.");
            } else if (lon1 > dificuldade.tesouro.lng) { // oeste
                return validarRetornoBounds(session, "Quer uma dica? O tesouro está ao OESTE de onde você está.");
            }
        }
        return "";
    }

    private String validarRetornoBounds(String session, String resposta) {
        if (respostasBounds.get(session) == null) {
            respostasBounds.put(session, resposta);
            return resposta;
        } else if (respostasBounds.get(session).equals(resposta)) {
            return "";
        }
        respostasBounds.put(session, resposta);
        return resposta;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @return Distance in Meters
     */
    private double calcularDistancia(double lat1, double lat2, double lon1,
                                     double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public enum Situacao {
        PERGUNTANDO_NOME,
        PERGUNTANDO_DIFICULDADE,
        DICA_1,
        JOGANDO
    }

    public enum Dificuldade {
        FACIL(10, Tesouro.MANE),
        MEDIO(20, Tesouro.BRAHMA),
        DIFICIL(30, Tesouro.GANSO);

        public Integer pontos;

        public Tesouro tesouro;

        Dificuldade(int i, Tesouro tesouro) {
            this.pontos = i;
            this.tesouro = tesouro;
        }
    }

    public enum Tesouro {
        GANSO("ChIJYTrUx7c6WpMRB8JHFLFtkSs", "Vamos lá! Encontre a melhor churrascaria de Brasília!", -15.815956149483142,-47.90207828260537, "O tesouro fica na Asa Sul"),
        BRAHMA("ChIJHdS939g6WpMRKi4dySPxt-g", "Vou facilitar pra você! Encontre o bar da número 1 pertinho da Matriz!", -15.804826882310508,-47.88462486946679, "O tesouro está na quadra comercial 201"),
        MANE("ChIJN1m37vU6WpMRWwejOaKabv0", "O tesouro está num lugar que custou R$2 bilhões para ser construído.", -15.78351938474289,-47.899211794137955, "O tesouro está onde aconteceu o Seleção CAIXA");

        public String local;

        public String descricaoLocal;

        public Double lat;

        public Double lng;

        public String dica;

        Tesouro(String local, String descricaoLocal, double lat, double lng, String dica) {
            this.local = local;
            this.descricaoLocal = descricaoLocal;
            this.lat = lat;
            this.lng = lng;
            this.dica = dica;
        }

        public static Tesouro buscarTesouro(String placeId) throws IllegalArgumentException {
            for (Tesouro tp : Tesouro.values()) {
                if (tp.local.equals(placeId)) return tp;
            }
            throw new IllegalArgumentException(Tesouro.class.getName() + " desconhecido: " + placeId);
        }
    }

}
