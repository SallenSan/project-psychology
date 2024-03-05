package com.br.psychology.system.psychologist_system.services.secretary;

import com.br.psychology.system.psychologist_system.models.Secretary;

public interface SecretaryService{
    public Secretary createUser(Secretary secretary);

    public boolean checkEmail(String email, Secretary secretary);

    Secretary getSecretaryById(Long id);

}
