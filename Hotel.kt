import kotlin.math.ceil

fun main() {
    while (true) {
        mostrarMenu()
        val escolha = readLine()?.toIntOrNull()

        when (escolha) {
            1 -> reservaQuartos()
            2 -> cadastroHospedes()
            3 -> pesquisarHospedes()
            4 -> eventoHotel()
            5 -> abastecerCarros()
            6 -> manutencaoAr()
            7 -> {
                println("Saindo do sistema... Até logo!")
                break
            }
            else -> erro()
        }
    }
}
// Função para exibir o menu de opções
fun mostrarMenu() {
    println("Bem-vindo ao sistema do Hotel Transilvânia! É um prazer ter você por aqui!")
    println("Escolha uma das opções:")
    println("1 -> Reserva de Quartos")
    println("2 -> Cadastro de Hóspedes")
    println("3 -> Pesquisar Hóspedes")
    println("4 -> Evento do Hotel")
    println("5 -> Abastecer Carros")
    println("6 -> Manutenção de Ar-Condicionado")
    println("7 -> Sair")
    print("Escolha uma opção: ")
}

// Lista para rastrear a ocupação dos quartos (inicialmente todos estão livres)

val quartosOcupados = MutableList(20) { false }

fun reservaQuartos() {

    println("Bem-vindo ao sistema de reservas do Hotel Transilvânia! É um prazer ter você por aqui!")

    val valorDiaria = obterValorDiaria()
    val diasHospedagem = obterDiasHospedagem()
    val nomeHospede = obterNomeHospede()
    val numeroQuarto = obterNumeroQuarto()

    // Calcula o valor total da hospedagem
    val valorTotal = valorDiaria * diasHospedagem

    println("O valor de $diasHospedagem dias de hospedagem é de R$$valorTotal")

    // Confirma a reserva
    if (confirmarReserva(nomeHospede, diasHospedagem, numeroQuarto, valorTotal)) {
        println("Reserva efetuada para $nomeHospede.")
        exibirStatusQuartos()
    } else {
        println("Reserva cancelada. Retornando ao menu inicial.")
        reservaQuartos()
    }
}
// Função para obter o valor da diária
fun obterValorDiaria(): Double {
    while (true) {
        print("Qual o valor padrão da diária? ")
        val valorDiaria = readLine()?.toDoubleOrNull()
        if (valorDiaria != null && valorDiaria > 0) {
            return valorDiaria
        } else {
            println("Valor inválido.")
        }
    }
}

// Função para obter o número de dias de hospedagem
fun obterDiasHospedagem(): Int {
    while (true) {
        print("Quantas diárias serão necessárias? ")
        val diasHospedagem = readLine()?.toIntOrNull()
        if (diasHospedagem != null && diasHospedagem in 1..30) {
            return diasHospedagem
        } else {
            println("Valor inválido.")
        }
    }
}

// Função para obter o nome do hóspede
fun obterNomeHospede(): String {
    print("Qual o nome do hóspede? ")
    return readLine().orEmpty()
}

// Função para obter o número do quarto
fun obterNumeroQuarto(): Int {
    while (true) {
        print("Qual o quarto para reserva? (1 - 20) ")
        val numeroQuarto = readLine()?.toIntOrNull()
        if (numeroQuarto != null && numeroQuarto in 1..20) {
            if (!quartosOcupados[numeroQuarto - 1]) {
                quartosOcupados[numeroQuarto - 1] = true
                return numeroQuarto
            } else {
                println("Quarto já está ocupado. Escolha outro quarto.")
            }
        } else {
            println("Número de quarto inválido.")
        }
    }
}

// Função para confirmar a reserva
fun confirmarReserva(nomeHospede: String, dias: Int, quarto: Int, valorTotal: Double): Boolean {
    print("$nomeHospede, você confirma a hospedagem por $dias dias para o quarto $quarto por R$$valorTotal? (S/N) ")
    val resposta = readLine().orEmpty().uppercase()
    return resposta == "S"
}

// Função para exibir o status dos quartos
fun exibirStatusQuartos() {
    println("Status dos quartos:")
    for (i in quartosOcupados.indices) {
        val status = if (quartosOcupados[i]) "Ocupado" else "Livre"
        print("${i + 1} - $status; ")
    }
    println()
}

// Função para cadastro de hóspedes
fun cadastroHospedes() {
    println("Bem-vindo ao cadastro de hóspedes do Hotel Transilvânia! É um prazer ter você por aqui!")
    print("Informe o valor da diária: ")
    val valorDiaria = readLine()?.toDoubleOrNull() ?: return

    var gratuidade = 0
    var meiaDiaria = 0
    var total = 0.0

    while (true) {
        print("Qual o nome do hóspede? ")
        val nome = readLine().orEmpty()
        if (nome.uppercase() == "PARE") break

        print("Qual a idade do hóspede? ")
        val idade = readLine()?.toIntOrNull() ?: continue

        when {
            idade < 6 -> {
                println("$nome possui gratuidade.")
                gratuidade++
            }
            idade > 60 -> {
                println("$nome paga meia.")
                meiaDiaria++
                total += valorDiaria / 2
            }
            else -> {
                total += valorDiaria
            }
        }
        println("$nome cadastrado(a) com sucesso.")
    }
    println("Valor total das hospedagens: R$$total; Gratuidade(s): $gratuidade; Meia(s): $meiaDiaria")
}

// Função para pesquisar hóspedes
// Lista para armazenar até 15 hóspedes
val listaHospedes = mutableListOf<String>()

fun pesquisarHospedes() {
    while (true) {
        println("\nEscolha uma opção:")
        println("1 -> Cadastrar Hóspede")
        println("2 -> Pesquisar Hóspede")
        println("3 -> Listar Hóspedes")
        println("4 -> Sair")
        print("Selecione uma opção: ")

        when (readLine()?.toIntOrNull()) {
            1 -> cadastrarHospede()
            2 -> pesquisarHospede()
            3 -> listarHospedes()
            4 -> {
                println("Saindo do menu de pesquisa de hóspedes.")
                return
            }
            else -> println("Opção inválida! Tente novamente.")
        }
    }
}

// Função para cadastrar um hóspede
fun cadastrarHospede() {
    if (listaHospedes.size >= 15) {
        println("Máximo de cadastros atingido.")
        return
    }

    print("Qual o nome do Hóspede? ")
    val nomeHospede = readLine().orEmpty().trim()

    if (nomeHospede.isNotEmpty()) {
        listaHospedes.add(nomeHospede)
        println("Hóspede $nomeHospede foi cadastrada(o) com sucesso!")
    } else {
        println("Nome inválido! Tente novamente.")
    }
}

// Função para pesquisar um hóspede
fun pesquisarHospede() {
    print("Qual o nome do Hóspede? ")
    val nomeHospede = readLine().orEmpty().trim()

    if (listaHospedes.contains(nomeHospede)) {
        println("Hóspede $nomeHospede foi encontrado(a)!")
    } else {
        println("Hóspede $nomeHospede não foi encontrado(a)!")
    }
}

// Função para listar todos os hóspedes cadastrados
fun listarHospedes() {
    if (listaHospedes.isEmpty()) {
        println("Nenhum hóspede cadastrado.")
    } else {
        println("Lista de hóspedes:")
        listaHospedes.forEach { hospede -> println(hospede) }
    }
}

// Função para gerenciar eventos do hotel

fun eventoHotel() {
    // 1: Onde será o evento?
    println("Quantidade de Convidados")
    print("Qual o número de convidados para o seu evento? ")
    val convidados = readLine()?.toIntOrNull() ?: -1

    if (convidados > 350 || convidados < 0) {
        println("Número de convidados inválido.")
        return
    }

    val auditorio = if (convidados <= 150) {
        val cadeirasExtras = if (convidados > 80) convidados - 150 else 0
        println("Use o auditório Laranja ${if (cadeirasExtras > 0) "(inclua mais $cadeirasExtras cadeiras)" else ""}")
        "Laranja"
    } else {
        println("Use o auditório Colorado")
        "Colorado"
    }
    println("Agora vamos ver a agenda do evento.")

    // Parte 2: Quando será o evento?
    println("\nAgenda")
    print("Qual o dia do evento? ")
    val diaEvento = readLine()?.lowercase() ?: ""

    print("Qual a hora do evento? ")
    val horaEvento = readLine()?.toIntOrNull() ?: -1

    val isDiaUtil = diaEvento in listOf("segunda", "terça", "quarta", "quinta", "sexta")
    val isFinalDeSemana = diaEvento in listOf("sabado", "domingo")
    val horarioPermitido = (isDiaUtil && horaEvento in 7..23) || (isFinalDeSemana && horaEvento in 7..15)

    if (!horarioPermitido) {
        println("Auditório indisponível")
        return
    }

    print("Qual o nome da empresa? ")
    val empresa = readLine().orEmpty()
    println("Auditório reservado para $empresa: $diaEvento às ${horaEvento}hs.")

    // Parte 3: Quantos trabalharão no evento?
    println("\nCálculo de garçons")
    print("Qual a duração do evento em horas? ")
    val duracaoHoras = readLine()?.toIntOrNull() ?: 0

    val garconsNecessarios = ceil(convidados / 12.0).toInt() + ceil(duracaoHoras / 2.0).toInt()
    val custoGarcons = garconsNecessarios * duracaoHoras * 10.50
    println("São necessários $garconsNecessarios garçons.")
    println("Custo: R$ $custoGarcons")

    // Parte 4: E quanto ao Buffet?
    println("\nBuffet")
    val litrosCafe = convidados * 0.2
    val litrosAgua = convidados * 0.5
    val quantidadeSalgados = convidados * 7

    val custoCafe = litrosCafe * 0.80
    val custoAgua = litrosAgua * 0.40
    val custoSalgados = ceil(quantidadeSalgados / 100.0).toInt() * 34.0
    val custoBuffet = custoCafe + custoAgua + custoSalgados

    println("O evento precisará de ${litrosCafe} litros de café, ${litrosAgua} litros de água, $quantidadeSalgados salgados.")
    println("Custo do Buffet: R$ $custoBuffet")

    // Parte 5: Conferência e confirmação da reserva
    println("\nConferência")
    val custoTotal = custoGarcons + custoBuffet
    println("Evento no Auditório $auditorio.")
    println("Nome da Empresa: $empresa.")
    println("Data: $diaEvento, ${horaEvento}hs.")
    println("Duração do evento: ${duracaoHoras}H.")
    println("Quantidade de garçons: $garconsNecessarios.")
    println("Quantidade de Convidados: $convidados")
    println("Custo do garçons: R$${"%.2f".format(custoGarcons)}")
    println("Custo do Buffet: R$${"%.2f".format(custoBuffet)}")
    println("Valor total do Evento: R$${"%.2f".format(custoTotal)}")

    print("Gostaria de efetuar a reserva? S/N: ")
    val confirmacao = readLine().orEmpty().uppercase()
    if (confirmacao == "S") {
        println("$empresa, reserva efetuada com sucesso.")
    } else {
        println("Reserva não efetuada.")
    }
}

// Função para abastecer carros
fun abastecerCarros() {
    // Variáveis para armazenar os preços dos combustíveis
    var alcoolWayne: Double
    var gasolinaWayne: Double
    var alcoolStark: Double
    var gasolinaStark: Double

    // Lê os preços do posto Wayne Oil
    println("Qual o valor do álcool no posto Wayne Oil?")
    alcoolWayne = readLine()!!.toDouble()

    println("Qual o valor da gasolina no posto Wayne Oil?")
    gasolinaWayne = readLine()!!.toDouble()

    // Lê os preços do posto Stark Petrol
    println("Qual o valor do álcool no posto Stark Petrol?")
    alcoolStark = readLine()!!.toDouble()

    println("Qual o valor da gasolina no posto Stark Petrol?")
    gasolinaStark = readLine()!!.toDouble()

    // Cálculo do custo total de abastecimento (42 litros)
    val litrosTanque = 42

    // Calculando o preço total para cada tipo de combustível nos postos
    val totalAlcoolWayne = alcoolWayne * litrosTanque
    val totalGasolinaWayne = gasolinaWayne * litrosTanque
    val totalAlcoolStark = alcoolStark * litrosTanque
    val totalGasolinaStark = gasolinaStark * litrosTanque

    // Verificando o preço do álcool em relação à gasolina
    val precoAlcoolMaisBaratoWayne = alcoolWayne <= (gasolinaWayne * 0.7)
    val precoAlcoolMaisBaratoStark = alcoolStark <= (gasolinaStark * 0.7)

    // Determinando o combustível mais barato e o posto
    when {
        precoAlcoolMaisBaratoWayne && (totalAlcoolWayne < totalGasolinaWayne) -> {
            println("É mais barato abastecer com álcool no posto Wayne Oil.")
        }
        precoAlcoolMaisBaratoStark && (totalAlcoolStark < totalGasolinaStark) -> {
            println("É mais barato abastecer com álcool no posto Stark Petrol.")
        }
        totalGasolinaWayne < totalGasolinaStark -> {
            println("É mais barato abastecer com gasolina no posto Wayne Oil.")
        }
        else -> {
            println("É mais barato abastecer com gasolina no posto Stark Petrol.")
        }
    }
}


// Função para manutenção de ar-condicionado
fun manutencaoAr() {
    val orcamentos = mutableListOf<Pair<String, Double>>() // Lista para armazenar os orçamentos (nome da empresa e valor)

    do {
        // Leitura dos dados da empresa
        println("Qual o nome da empresa?")
        val nomeEmpresa = readLine() ?: ""

        println("Qual o valor por aparelho?")
        val valorPorAparelho = readLine()!!.toDouble()

        println("Qual a quantidade de aparelhos?")
        val quantidadeAparelhos = readLine()!!.toInt()

        println("Qual a porcentagem de desconto?")
        val percentualDesconto = readLine()!!.toDouble()

        println("Qual o número mínimo de aparelhos para conseguir o desconto?")
        val quantidadeMinimaDesconto = readLine()!!.toInt()

        // Cálculo do valor total do serviço
        var valorTotal = valorPorAparelho * quantidadeAparelhos

        // Aplicação do desconto se a quantidade de aparelhos for suficiente
        if (quantidadeAparelhos >= quantidadeMinimaDesconto) {
            valorTotal -= valorTotal * (percentualDesconto / 100)
        }

        // Armazenando o orçamento
        orcamentos.add(Pair(nomeEmpresa, valorTotal))

        // Exibindo o valor total do serviço
        println("O serviço de $nomeEmpresa custará R$ ${"%.2f".format(valorTotal)}")

        // Pergunta se deseja informar novos dados
        println("Deseja informar novos dados? (S/N)")
    } while (readLine()?.uppercase() == "S")

    // Encontrando o orçamento de menor valor
    val menorOrcamento = orcamentos.minByOrNull { it.second }
    menorOrcamento?.let {
        println("O orçamento de menor valor é o de ${it.first} por R$ ${"%.2f".format(it.second)}")
    }
}

// Função de erro para opção inválida
fun erro() {
    println("Opção inválida! Tente novamente.")
}


