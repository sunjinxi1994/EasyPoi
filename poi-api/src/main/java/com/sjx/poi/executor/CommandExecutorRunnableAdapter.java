package com.sjx.poi.executor;

import com.sjx.poi.core.TransferCommandExecutor;
import com.sjx.poi.request.RequestController;

import java.util.jar.Attributes;

/**
 * @author : sunjinxi
 * @Description: TODO
 * @date Date : 2021年01月31日 22:44
 **/
public class CommandExecutorRunnableAdapter extends NamedRunnable implements RequestController {

    private TransferCommandExecutor commandExecutor;
    public CommandExecutorRunnableAdapter(TransferCommandExecutor transferCommandExecutor){
        this.commandExecutor=transferCommandExecutor;
    }

    @Override
    protected void execute() throws Throwable {
          commandExecutor.executeCommand(commandExecutor.getArguments());
    }

    @Override
    public void start() {
        commandExecutor.start();
    }

    @Override
    public void pause() {
        commandExecutor.pause();
    }

    @Override
    public void resume() {
        commandExecutor.resume();

    }

    @Override
    public void stop() {
        commandExecutor.stop();

    }

    @Override
    public void await() {
        commandExecutor.await();
    }
}