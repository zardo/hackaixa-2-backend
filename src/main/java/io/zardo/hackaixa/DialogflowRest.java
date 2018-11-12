package io.zardo.hackaixa;

import io.zardo.hackaixa.dialogflow.DialogflowRequest;
import io.zardo.hackaixa.dialogflow.DialogflowResponse;
import io.zardo.hackaixa.dialogflow.DialogflowService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mensagem")
public class DialogflowRest {

    private DialogflowService dialogflowService = DialogflowService.getInstance();

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON + ";charset=\"utf-8\"")
    public Response tratarMensagem(DialogflowRequest dialogflowRequest) {
        DialogflowResponse dialogflowResponse = new DialogflowResponse();
        String session = dialogflowRequest.getSession();

        switch (dialogflowRequest.getQueryResult().getIntent().getDisplayName()) {

            case "smalltalk.greetings.hello":
                dialogflowResponse.addResponseText("Bem-vindo à Caça ao Tesouro Hackaixa!");
                dialogflowResponse.addResponseText("O jogo funciona assim: eu dou uma dica e você precisa procurar o ponto no mapa até encontrá-lo. Quando encontrá-lo, é só clicar que eu falo se está certo.");
                dialogflowResponse.addResponseText("Vamos lá! Qual é seu nome?");
                dialogflowService.setarSituacaoSessao(session, DialogflowService.Situacao.PERGUNTANDO_NOME);
                break;

            case "respondendo_nome":
                dialogflowResponse.addResponseText(String.format("Perfeito, %s! Em qual dificuldade você quer jogar? Fácil, médio ou difícil?",
                        dialogflowRequest.getQueryResult().getParameters().getNome()));
                dialogflowService.setarNome(session, dialogflowRequest.getQueryResult().getParameters().getNome());
                dialogflowService.setarSituacaoSessao(session, DialogflowService.Situacao.PERGUNTANDO_DIFICULDADE);
                break;

            case "smalltalk.agent.can_you_help":
            case "smalltalk.user.needs_advice":
            case "fallback":
                switch (dialogflowService.buscarSituacaoSessao(session)) {
                    case PERGUNTANDO_NOME:
                        dialogflowResponse.addResponseText("Desculpe. Não sou um robô muito esperto. Podemos voltar onde a gente estava?");
                        dialogflowResponse.addResponseText("Qual é seu nome?");
                        break;
                    case PERGUNTANDO_DIFICULDADE:
                        dialogflowResponse.addResponseText("Desculpe. Não sou um robô muito esperto. Podemos voltar onde a gente estava?");
                        dialogflowResponse.addResponseText("Em qual dificuldade você quer jogar? Fácil, médio ou difícil?");
                        break;
                    case JOGANDO:
                        dialogflowResponse.addResponseText("Aí vai uma dica:");
                        dialogflowResponse.addResponseText(dialogflowService.pedirAjuda(session));
                        break;
                }
                break;

            case "brahma":
            case "mane_garrincha":
            case "ganso":
                switch (dialogflowService.buscarSituacaoSessao(session)) {
                    case PERGUNTANDO_NOME:
                        dialogflowResponse.addResponseText("Antes de responder quero saber seu nome!");
                        break;
                    case PERGUNTANDO_DIFICULDADE:
                        dialogflowResponse.addResponseText("Em qual dificuldade você quer jogar mesmo?");
                        break;
                    case JOGANDO:
                        switch (dialogflowRequest.getQueryResult().getIntent().getDisplayName()){
                            case "brahma":
                                if (dialogflowService.validarResposta(session, DialogflowService.Tesouro.BRAHMA)) {
                                    dialogflowResponse.addResponseText(String.format("Aeee! Parabéns, %s! Eu sabia que você ia conseguir!", dialogflowService.buscarNome(session)));
                                } else {
                                    dialogflowResponse.addResponseText(String.format("Ainda não foi dessa vez, %s! Tente de novo!", dialogflowService.buscarNome(session)));
                                }
                                break;

                            case "mane_garrincha":
                                if (dialogflowService.validarResposta(session, DialogflowService.Tesouro.MANE)) {
                                    dialogflowResponse.addResponseText(String.format("Aeee! Parabéns, %s! Eu sabia que você ia conseguir!", dialogflowService.buscarNome(session)));
                                } else {
                                    dialogflowResponse.addResponseText(String.format("Ainda não foi dessa vez, %s! Tente de novo!", dialogflowService.buscarNome(session)));
                                }
                                break;

                            case "ganso":
                                if (dialogflowService.validarResposta(session, DialogflowService.Tesouro.GANSO)) {
                                    dialogflowResponse.addResponseText(String.format("Aeee! Parabéns, %s! Eu sabia que você ia conseguir!", dialogflowService.buscarNome(session)));
                                } else {
                                    dialogflowResponse.addResponseText(String.format("Ainda não foi dessa vez, %s! Tente de novo!", dialogflowService.buscarNome(session)));
                                }
                                break;
                        }
                        break;
                }
                break;

            case "facil":
                dialogflowService.setarDificuldade(session, DialogflowService.Dificuldade.FACIL);
                dialogflowService.setarSituacaoSessao(session, DialogflowService.Situacao.JOGANDO);
                dialogflowResponse.addResponseText("Tudo bem. Nem todo mundo joga no hard.");
                dialogflowResponse.addResponseText(dialogflowService.buscarDica(session));
                break;

            case "medio":
                dialogflowService.setarDificuldade(session, DialogflowService.Dificuldade.MEDIO);
                dialogflowService.setarSituacaoSessao(session, DialogflowService.Situacao.JOGANDO);
                dialogflowResponse.addResponseText("Achei que você ia jogar no difícil, mas eu entendo, todo mundo tem medo dos meus desafios.");
                break;

            case "dificil":
                dialogflowService.setarDificuldade(session, DialogflowService.Dificuldade.DIFICIL);
                dialogflowService.setarSituacaoSessao(session, DialogflowService.Situacao.JOGANDO);
                dialogflowResponse.addResponseText("Eu sabia que você não me decepcionaria. Que comecem os jogos! :D");
                break;

            case "bounds":
                if (dialogflowService.buscarSituacaoSessao(session).equals(DialogflowService.Situacao.JOGANDO)) {
                    dialogflowResponse.addResponseText(dialogflowService.compararBounds(session, dialogflowRequest.getQueryResult().getParameters()));
                }
                break;

            case "poi":
                if (dialogflowService.validarResposta(session, dialogflowRequest.getQueryResult().getParameters().getPlaceId())) {
                    dialogflowResponse.addResponseText(String.format("Aeee! Parabéns, %s! Eu sabia que você ia conseguir!", dialogflowService.buscarNome(session)));
                } else {
                    dialogflowResponse.addResponseText(String.format("Ainda não foi dessa vez, %s! Tente de novo!", dialogflowService.buscarNome(session)));
                }
                break;
        }

        return Response.status(Response.Status.CREATED).entity(dialogflowResponse).build();
    }

}
