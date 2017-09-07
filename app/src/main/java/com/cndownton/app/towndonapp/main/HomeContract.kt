package com.cndownton.app.towndonapp.main

import com.cndownton.app.towndonapp.BasePresenter
import com.cndownton.app.towndonapp.BaseView

/**
 * Created by Mpf on 2017/9/3.
 */
interface HomeContract {
    interface View:BaseView<Presenter>{

    }

    interface Presenter:BasePresenter{

    }
}