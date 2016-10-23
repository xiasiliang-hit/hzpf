package com.line.bqxd.platform.velocity;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.Toolbox;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by huangjianfei on 16/5/18.
 */
public class CustomVelocityToolboxView extends VelocityLayoutView {
    private ToolManager tm = null;

    private Toolbox requestToolbox = null;

    private Toolbox applicationToolbox = null;

    private Toolbox sessionToolbox = null;


    @Override
    protected Context createVelocityContext(Map<String, Object> model,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {// Create a
        // ChainedContext
        // instance.
        ViewToolContext ctx;

        ctx = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());

        ctx.putAll(model);
        if (tm == null) {
            synchronized (this) {
                if (this.getToolboxConfigLocation() != null) {
                    tm = new ToolManager();
                    tm.setVelocityEngine(getVelocityEngine());
                    tm.configure(getServletContext().getRealPath(
                            getToolboxConfigLocation()));
                    if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
                        requestToolbox = tm.getToolboxFactory().createToolbox(Scope.REQUEST);
                        //ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.REQUEST));
                    }
                    if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
                        applicationToolbox = tm.getToolboxFactory().createToolbox(Scope.APPLICATION);
                        //ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.APPLICATION));
                    }
                    if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
                        sessionToolbox = tm.getToolboxFactory().createToolbox(Scope.SESSION);
                        //ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.SESSION));
                    }
                }
            }
        }

        if (requestToolbox != null) {
            ctx.addToolbox(requestToolbox);
        }
        if (applicationToolbox != null) {
            ctx.addToolbox(applicationToolbox);
        }
        if (sessionToolbox != null) {
            ctx.addToolbox(sessionToolbox);
        }

        return ctx;
    }
}