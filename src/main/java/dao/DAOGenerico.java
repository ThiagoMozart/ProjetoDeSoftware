package dao;


import excecao.ObjetoNaoEncontradoException;

public interface DAOGenerico<T, PK>{
    T inclui(T umObjeto);
    void altera(T umObjeto);
    void exclui(T umObjeto);
    T getPorId(PK umId) throws ObjetoNaoEncontradoException;
    T getPorIdComLock(PK umId) throws ObjetoNaoEncontradoException;
    T busca(PK umId) throws ObjetoNaoEncontradoException;
    T buscaLista(PK umId) throws ObjetoNaoEncontradoException;
}
