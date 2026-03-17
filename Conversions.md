# From RGB To HSL:

Values must be in range $R, G, B \in [0, 1]$.

With maximum component (_i.e._ value)

$$ X_{max} := max(R, G, B) =: V $$

and minimum component

$$ X_{min} := min(R, G, B) = V - C $$

range(_i.e._ chroma)

$$ C := X_{max} - X_{min} = 2(V - L) $$

and mid-range (_i.e._ lightness)

$$ L := mid(R, G, B) = \frac{X_{max} - X_{min}}{2} = V - \frac{C}{2} $$

we get common hue:

$$
H :=
\begin{cases}
0, & \text{if } C = 0 \\
60\degree \cdot \left(\frac{G - B}{C} \mod 6\right), & \text{if } V = R \\
60\degree \cdot \left(\frac{B - R}{C} + 2\right), & \text{if } V = G
60\degree \cdot \left(\frac{R - G}{C} + 4\right), & \text{if } V = B
\end{cases}
$$

and distinct saturations:

$$
S_V :=
\begin{cases}
0, & \text{if } V = 0 \\
\frac{C}{V}, & \text{otherwise}
\end{cases}
$$

$$
S_L :=
\begin{cases}
0, & \text{if } L = 0 \text{ or } L = 1\\
\frac{C}{1 - \left|2V - C - 1 \right|} = \frac{2(V - L)}{1 - \left|2L - 1\right|} = \frac{V - L}{\min(L, 1 - L)}, & \text{otherwise}
\end{cases}
$$
