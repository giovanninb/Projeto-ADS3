/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import DTO.PesquisaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Giova
 */
public class PesquisaDao {
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    private final Conexao conexao = new Conexao();

    public PesquisaDTO consultar(String nome) {
        conn = new Conexao().getConexao();
        try {
            PesquisaDTO dto = new PesquisaDTO();
            st = conn.prepareStatement("SELECT * FROM teste WHERE nm_nome = ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            if (rs.next()) { // se encontrou o funcionário
                dto.setCodigo(rs.getInt("codigo"));
                dto.setNome(rs.getString("nm_nome"));
                dto.setStatus(rs.getString("status"));
                dto.setValor(rs.getInt("valor"));
                return dto;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }

    }

   public List<PesquisaDTO> getLista(String nome) throws SQLException {
        conn = new Conexao().getConexao();
        try {
            st = conn.prepareStatement("select * from teste WHERE nm_nome like ?");
            st.setString(1, nome);
            rs = st.executeQuery();
            List<PesquisaDTO> minhaLista = new ArrayList<PesquisaDTO>();
            while (rs.next()) { // se encontrou o funcionário
                PesquisaDTO c1 = new PesquisaDTO();
                c1.setCodigo(rs.getInt("codigo"));
                c1.setNome(rs.getString("nm_nome"));
                c1.setStatus(rs.getString("status"));
                c1.setValor(rs.getDouble("valor"));
                minhaLista.add(c1);
            }
            rs.close();
            st.close();
            return minhaLista;
        } catch (SQLException ex) {
            return null;
        }
    }
   
    public void adiociona(PesquisaDTO dto) throws SQLException {
        conn = new Conexao().getConexao();
        System.out.println(dto.getCodigo() + "\n" + dto.getNome() + "\n" + dto.getStatus() + "\n" + dto.getValor());
        String sql = "insert into teste(nm_nome, status,valor) values (?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, dto.getNome());
        stmt.setString(2, dto.getStatus());
        stmt.setDouble(3, dto.getValor());

        stmt.execute();
        stmt.close();
    }

    public void remove(PesquisaDTO dto) throws SQLException {
        conn = new Conexao().getConexao();
        String sql = "delete from teste where codigo=?";
        System.out.println(dto.getCodigo());
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, dto.getCodigo());
        stmt.execute();
        stmt.close();
    }

    public void update (PesquisaDTO dto) throws SQLException{
       conn = new Conexao().getConexao();
        try
            (PreparedStatement pst = conexao.getConexao().prepareStatement
            ("update teste set nm_nome = ?, status = ?, valor = ? " +
             "where (codigo = ?)")){
            pst.setString(1, dto.getNome());
            pst.setString(2, dto.getStatus());
            pst.setDouble(3, dto.getValor());
            pst.setInt(4, dto.getCodigo());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alterado com sucesso !");
           }
            conexao.close();
        }
}
