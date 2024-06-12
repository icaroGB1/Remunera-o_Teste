package crud;

import java.util.List;

import Entidades.ItemVenda;
import Entidades.Produto;
import Entidades.Venda;

public interface ItemVendaCRUD {
	void cadastrar(ItemVenda itemVenda, Venda venda, Produto produto);
    void atualizar(ItemVenda itemVenda);
    void excluir(ItemVenda itemVenda);
    List<ItemVenda> consultar();

}
