# Net Tracker

Net Tracker é um aplicativo Java para varredura de redes IP em uma sub-rede configurável. Ele permite escanear hosts e identificar se estão ativos, exibindo os resultados em uma interface gráfica com suporte a múltiplas estratégias de execução.

## Lógica central das classes

A aplicação é dividida em três partes principais: interface gráfica, controle do fluxo da varredura e gerenciamento das threads de execução.

- **MainWindow**: representa a interface gráfica, contendo os painéis para configuração da rede, exibição dos resultados e status da execução.
- **MainWindowController**: atua como intermediário entre a interface e a lógica da varredura, recebendo eventos da interface e coordenando o processo de scan.
- **ScanController**: gerencia o processo de varredura, enviando tarefas para execução e notificando os listeners sobre o progresso.
- **ThreadManager** e suas implementações: definem diferentes estratégias para execução das tarefas (sem threads, single thread, múltiplas threads fixas e threads dinâmicas).
- **Host**: representa um host da rede, contendo endereço IP e estado de conexão.

## Fluxo de execução

Ao iniciar a varredura, o usuário seleciona a configuração da rede e o modo de execução. O MainWindowController valida a configuração, cria o ThreadManager correspondente à estratégia escolhida e inicia o ScanController. Este, por sua vez, submete as tarefas para o ThreadManager executar.

Durante o scan, o ScanController notifica o MainWindowController, que atualiza a interface com os hosts encontrados, a barra de progresso e o timer. O processo pode ser interrompido via botão de parada, que cancela as tarefas e atualiza o status.

## Padrões de projeto utilizados

- **Strategy**: aplicado no gerenciamento das threads, onde diferentes implementações de ThreadManager permitem trocar a estratégia de execução sem alterar o fluxo principal.
- **Observer/Listener**: o ScanController notifica o MainWindowController sobre eventos da varredura (host escaneado, finalização), permitindo atualização assíncrona da interface.
