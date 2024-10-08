# busca-fruta
Resumo do App BuscaFruta
O aplicativo BuscaFruta permite que os usuários localizem árvores frutíferas em um mapa interativo, utilizando a API do Google Maps. O app é projetado para filtrar e exibir as localizações de diferentes tipos de frutas, ajudando os usuários a encontrar rapidamente o que procuram. Além disso, o aplicativo utiliza geofencing para enviar notificações relevantes ao usuário.

Funcionalidades Principais:
Visualização de Mapa: Os usuários podem visualizar a localização das árvores frutíferas em um mapa interativo.
Filtragem de Frutas: O usuário pode filtrar as árvores de frutas por nome, e a lista é atualizada dinamicamente.
Geofencing: O aplicativo implementa geofences para monitorar a proximidade das frutas e enviar notificações quando o usuário se aproxima delas.
Permissões: O app gerencia as permissões de localização e notificações, garantindo que o usuário tenha controle sobre os dados acessados.
Aspectos Técnicos e Estrutura
Arquitetura: O app segue o padrão Clean Architecture, separando a lógica de apresentação, domínio e dados, o que facilita a manutenção e testabilidade do código.
ViewModel: Utiliza o padrão ViewState com o ViewModel do Android, garantindo que o estado da interface do usuário seja mantido durante as mudanças de configuração.
Coroutines e Flow: O uso de Coroutines e Flow permite a manipulação assíncrona dos dados, proporcionando uma experiência de usuário mais responsiva e fluida.
Unit Tests: O app inclui testes unitários para o FruitTreeViewModel, assegurando que a lógica de filtragem e o estado inicial estejam funcionando conforme o esperado. Os testes utilizam a biblioteca JUnit e kotlinx.coroutines para simular comportamentos assíncronos.
Princípios de Clean Code: O código é estruturado seguindo as práticas do Clean Code, com ênfase na legibilidade e manutenibilidade. Os nomes de variáveis e métodos são descritivos, e a lógica é dividida em funções menores e coesas.

Conclusão
O BuscaFruta é um aplicativo bem estruturado e testado, que utiliza boas práticas de desenvolvimento para oferecer uma experiência rica e interativa aos usuários. A arquitetura e os padrões adotados facilitam a expansão e manutenção do aplicativo, enquanto os testes garantem a integridade da lógica implementada.