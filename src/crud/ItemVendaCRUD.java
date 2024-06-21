package crud;

import java.sql.SQLException;
import java.util.List;

import Entidades.ItemVenda;
import Entidades.Produto;
import Entidades.Venda;

public interface ItemVendaCRUD {
	void cadastrar(ItemVenda itemVenda, Venda venda,List<ItemVenda> itensVenda);
    void atualizar(ItemVenda itemVenda);
    void excluir(int idItemVenda);
    List<ItemVenda> consultar();
    ItemVenda consultarPorId(int id) throws SQLException;
    List<ItemVenda> consultarPorVenda(int idVenda);
}
