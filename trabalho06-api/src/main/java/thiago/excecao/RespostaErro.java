package thiago.excecao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@Getter
public class RespostaErro {
    private LocalDateTime dataHora;
    private int codidoDeErro;
    private String erro;
    private String metodo;
    private String requsicaoUri;
    private Map<String, String> map;
    private String mensagem;
}