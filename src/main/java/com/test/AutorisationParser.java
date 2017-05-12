package com.test;

import com.aad.myutil.server.client.MYAuthorizationIF;
import com.aad.myutil.server.client.MYClientIF;
import com.aad.myutil.server.client.MYLoginIF;

import java.util.List;

/**
 * Created by Taras on 13.04.2017.
 */
public class AutorisationParser implements MYAuthorizationIF {
    public MYClientIF login(MYLoginIF myLoginIF) {
        return null;
    }

    public MYClientIF registration(MYLoginIF myLoginIF) {
        return null;
    }

    public void loggon(long l) {

    }

    public List<Long> getClientsAll(boolean b) {
        return null;
    }
}
