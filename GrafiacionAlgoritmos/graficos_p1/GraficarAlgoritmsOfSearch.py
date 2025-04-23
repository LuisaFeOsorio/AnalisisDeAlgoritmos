import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import os
from pathlib import Path

def setStyle_design():
    sns.set(style="whitegrid")
    plt.figure(figsize=(14, 8))

def configurarData(rutaRecursoCSV):
    # Lectura de datos Limpieza y preparación de datos
    ruta_csv = rutaRecursoCSV
    df = pd.read_csv(ruta_csv)

    #------------valores absolutos----------------
    df['Tiempo(ms)'] = pd.to_numeric(
        df['Tiempo(ms)'].astype(str).str.replace(',', '').str.strip(),
        errors='coerce'
    ).abs()  # Asegura valores positivos


    df = df.dropna(subset=['Tiempo(ms)'])
    return df

def definirOrden_escalas(df):
    # Orden personalizado para tamaños y algoritmos
    tamanios_orden = ['10000', '100000', '1000000']
    algoritmos_orden = ['Bubble Sort', 'Stooge Sort', 'Bitonic Sort',
                        'Radix Sort', 'Merge Sort', 'Quick Sort']  # Orden de lento a rápido

    df['tam_array'] = pd.Categorical(
        df['tam_array'].astype(str),
        categories=tamanios_orden,
        ordered=True
    )

    df['Algoritmo'] = pd.Categorical(
        df['Algoritmo'],
        categories=algoritmos_orden,
        ordered=True
    )
    return df

def graficar_escalas(df):
    # Crear dos subplots: uno para escala normal y otro logarítmica
    fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(16, 14))

    palette = sns.color_palette("husl", n_colors=len(df['tam_array'].unique()))

    # Grf1: Escala normal
    sns.barplot(
        x='Algoritmo',
        y='Tiempo(ms)',
        hue='tam_array',
        data=df,
        ax=ax1,
        palette=palette
    )
    ax1.set_title('Comparación de Algoritmos de Ordenamiento (Escala Lineal)')
    ax1.set_ylabel('Tiempo (ms)')
    ax1.legend(title='Tamaño del arreglo')

    # Grf2: Escala logarítmica (para ver todos los algoritmos)
    sns.barplot(
        x='Algoritmo',
        y='Tiempo(ms)',
        hue='tam_array',
        data=df,
        ax=ax2,
        palette=palette
    )
    ax2.set_yscale('log')  # Escala logarítmica
    ax2.set_title('Comparación de Algoritmos de Ordenamiento (Escala Logarítmica)')
    ax2.set_ylabel('Tiempo (ms) - Escala Log')
    ax2.legend(title='Tamaño del arreglo')

    # Ajustes comunes
    for ax in [ax1, ax2]:
        ax.set_xlabel('Algoritmo')
        ax.tick_params(axis='x', rotation=45)

        for container in ax.containers:
            ax.bar_label(container, fmt='%.1f ms', padding=3, fontsize=8)

    plt.tight_layout()
    #plt.show()
    #----------------GUARDADO DE GRAFICA DE ALGORITMOS DE ORDENAMIENTO-------------------
    plt.tight_layout()
    plt.savefig('Grafica_alg_ordenamiento.png', dpi=300, bbox_inches='tight')
    print("Gráfica Algoritmos de ordenamiento,guardada  como 'Grafica_alg_ordenamiento.png'")

def main():
    RUTA_RELATIVA = Path(__file__).parent.parent.parent / 'metaData' / 'metaData_punto1.csv'

    if not os.path.exists(RUTA_RELATIVA):
        raise FileNotFoundError(f"No se encontró el CSV en: {RUTA_RELATIVA}")

    #-----------EJECUCIÓN INICIAL DEL PROGRAMA----------------
    setStyle_design()

    #-----------LIMPIEZA Y PREPARACIÓN DE METADATA------------
    df = configurarData(RUTA_RELATIVA)

    #-----------ORDENAMIENTO DE ESCALAS----------------------
    df = definirOrden_escalas(df)

    #-----------GRAFICACIÓN DE ESCALAS----------------------
    graficar_escalas(df)

if __name__ == '__main__':
    main()