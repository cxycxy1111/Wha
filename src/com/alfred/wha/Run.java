package com.alfred.wha;

import com.alfred.wha.serv.AdminUserService;

public class Run {

    public static void main(String args[]) {
        AdminUserService adminUserService = new AdminUserService();
        adminUserService.add(
                Long.parseLong(args[0]),
                args[1],
                args[2],
                Integer.parseInt(args[3]),
                args[4],
                Long.parseLong(args[5]));
    }

}
