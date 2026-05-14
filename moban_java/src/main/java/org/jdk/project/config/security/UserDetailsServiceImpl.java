package org.jdk.project.config.security;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.jdk.project.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** 基于用户ID加载用户与权限信息的服务实现。 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  /**
   * 根据用户ID加载用户详情（包含角色权限）。
   *
   * @param id 用户ID（字符串形式）
   * @return Spring Security UserDetails
   */
  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    org.jooq.generated.project.tables.pojos.User dbUser = userRepository.fetchOneById(Long.valueOf(id));
    if (dbUser == null) {
      throw new UsernameNotFoundException(String.format("uid %s user not found", id));
    }
    return new User(
        dbUser.getUsername(),
        dbUser.getPassword(),
        dbUser.getEnable() == null || dbUser.getEnable(),
        true,
        true,
        true,
        Collections.<SimpleGrantedAuthority>emptySet());
  }
}
