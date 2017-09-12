package com.cndownton.app.downton.main

import com.cndownton.app.downton.BasePresenter
import com.cndownton.app.downton.BaseView

/**
 * Created by Mpf on 2017/9/3.
 */
interface HomeContract {
    interface View:BaseView<Presenter>{

    }

    interface Presenter:BasePresenter{

    }
}