package ifrn.pi.comercio.config.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ifrn.pi.comercio.models.Usuario;
import ifrn.pi.comercio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	final UsuarioRepository usuarioRepository;
	
	public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNome(nome)
				.orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado com esse nome: " + nome));
		return new User(usuario.getNome(), usuario.getSenha(), true, true, true, true, usuario.getAuthorities());
	}

}
